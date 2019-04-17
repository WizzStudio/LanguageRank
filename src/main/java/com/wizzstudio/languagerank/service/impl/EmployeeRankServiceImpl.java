package com.wizzstudio.languagerank.service.impl;


import com.wizzstudio.languagerank.dao.LanguageDAO;
import com.wizzstudio.languagerank.dao.employeerankDAO.CompanyPostDAO;
import com.wizzstudio.languagerank.dao.employeerankDAO.CompanySalaryDAO;
import com.wizzstudio.languagerank.dao.employeerankDAO.EmployeeRankDAO;
import com.wizzstudio.languagerank.dao.employeerankDAO.LanguageCityDAO;
import com.wizzstudio.languagerank.domain.*;
import com.wizzstudio.languagerank.dto.EmployeeRankDTO;
import com.wizzstudio.languagerank.service.EmployeeRankService;
import com.wizzstudio.languagerank.service.LanguageTendService;
import com.wizzstudio.languagerank.util.DateUtil;
import com.wizzstudio.languagerank.util.DoubleUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class EmployeeRankServiceImpl implements EmployeeRankService {

    @Autowired
    EmployeeRankDAO employeeRankDAO;
    @Autowired
    LanguageCityDAO languageCityDAO;
    @Autowired
    CompanySalaryDAO companySalaryDAO;
    @Autowired
    CompanyPostDAO companyPostDAO;
    @Autowired
    EmployeeRankService employeeRankService;
    @Autowired
    LanguageTendService languageTendService;
    @Autowired
    LanguageDAO languageDAO;

    @Override
    public Double findSalaryExponent(String languageName) {
        TreeSet<Double> treeSet = new TreeSet<>();
        // 薪资指数算法中的m值
        double m = 0.0;
        // 薪资指数算法中的a值
        double a = 0.0;
        // 所需查语言的a值
        double special_a = 0.0;

        // 计算所有语言的a值，取前十的平均值记为m
        List<Language> languageList = languageDAO.findAll();
        for (Language language : languageList){
            String languageNameRank = language.getLanguageName();

            // 取排名前五公司的平均薪资
            List<CompanySalary> companySalaries = companySalaryDAO.findTopFiveByLanguageName(languageNameRank);
            for(CompanySalary companySalary : companySalaries){
                a += companySalary.getCompanyOrdSalary();
            }
            a = a / 5;
            treeSet.add(a);
            if (languageNameRank.equals(languageName)) {
                special_a = a;
            }
            a = 0;
        }
        for (int i = 0;i < 10;i++) {
            try {
                m += treeSet.pollLast();
            } catch (NullPointerException e) {
                log.error("获取薪资指数失败");
            }
        }

        m = m / 10;
        return 30 * special_a / m;
    }

    @Override
    public Double findCityExponent(String languageName) {
        double topSum = 0.0;
        double allSum = languageCityDAO.findLanguageAllSum(languageName);
        List<LanguageCity> languageCityList = languageCityDAO.findLanguageCityTopFiveByLanguageName(languageName);
        for (int i = 0; i < 5; i++)
            topSum = topSum + languageCityList.get(i).getCityPostNumber();

        double rate = topSum / allSum;
        return 15 * rate + 5 * (1 - rate);
    }

    @Override
    public Double findLanguagePostNumber(String languageName) {
//        Map<String, Integer> map = new HashMap<>();
        TreeSet<Integer> treeSet = new TreeSet<>();
        // 某种语言的总需求量
        int number = 0;
        // 需求量前十语言的平均需求量
        double a = 0.0;
        int special_number = 0;

        List<Language> languageList = languageDAO.findAll();

        // 计算所有语言的需求量，取前十的平均值做分母
        for (Language language : languageList){
            String languageNameRank = language.getLanguageName();

            // 计算该语言所有公司的岗位数之和
            List<CompanyPost> companyPosts = companyPostDAO.findCompanyPostByLanguageName(languageNameRank);
            for (CompanyPost companyPost : companyPosts){
                number += companyPost.getCompanyPostNumber();
            }
            if (languageNameRank.equals(languageName)) {
                special_number = number;
            }
            treeSet.add(number);
            number = 0;
        }
        for (int i = 0;i < 10;i++) {
            try {
                a += treeSet.pollLast();
            } catch (NullPointerException e) {
                log.error("获取需求量指数失败");
            }
        }
        a = a/10;

        return 20 * special_number / a ;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveExponent() {
        List<Language> languageList = languageDAO.findAll();

        for (Language language : languageList) {
            String temporaryLanguageName = language.getLanguageName();

            double cityExponent = findCityExponent(temporaryLanguageName);
            double languagePostNumberExponent = findLanguagePostNumber(temporaryLanguageName);
            double salaryExponent = findSalaryExponent(temporaryLanguageName);
            double exponent = cityExponent + languagePostNumberExponent + salaryExponent;
            if (exponent > 100) {
                exponent = 100.0;
            }

            EmployeeRank employeeRank = new EmployeeRank();
            employeeRank.setLanguageName(temporaryLanguageName);
            employeeRank.setCityPostExponent(cityExponent);
            employeeRank.setLanguagePostExponent(languagePostNumberExponent);
            employeeRank.setSalaryExponent(salaryExponent);
            employeeRank.setEmployeeFinalExponent(DoubleUtil.getDecimalFormat(exponent));
            try {
                employeeRank.setUpdateTime(DateUtil.getNextDate(new Date()));
            } catch (ParseException e) {
                log.error("时间转换异常");
                e.printStackTrace();
            }

            employeeRankDAO.save(employeeRank);
        }
    }

    private static List<EmployeeRankDTO> list = new ArrayList<>();

    @Override
    public List<EmployeeRankDTO> getEmployeeRank() {
//        // 测试用
//        saveExponent();
        if (!list.isEmpty()) {
            return list;
        }

        List<EmployeeRankDTO> employeeRankDTOList = new ArrayList<>();

        List<EmployeeRank> employeeRanks = employeeRankDAO.findTopTenLanguage();
        for (EmployeeRank employeeRank : employeeRanks){

            EmployeeRankDTO employeeRankDTO = new EmployeeRankDTO();
//            获取前十的语言名称
            String languageName = employeeRank.getLanguageName();

//            雇主需求榜四个字段
            employeeRankDTO.setLanguageName(languageName);
            employeeRankDTO.setLanguageSymbol(languageDAO.findByLanguageName(languageName).getLanguageSymbol());
            employeeRankDTO.setLanguageTend(languageTendService.findEmployeeLanguageTendNumber(languageName));
            employeeRankDTO.setEmployeeFinalExponent(employeeRankDAO.findByLanguageName(languageName).getEmployeeFinalExponent());

            employeeRankDTOList.add(employeeRankDTO);
        }

        list = employeeRankDTOList;
        return employeeRankDTOList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetList() {
        list = new ArrayList<>();
    }
}
