package com.wizzstudio.languagerank.service.impl;


import com.wizzstudio.languagerank.dao.LanguageDAO;
import com.wizzstudio.languagerank.dao.employeerankDAO.CompanyPostDAO;
import com.wizzstudio.languagerank.dao.employeerankDAO.CompanySalaryDAO;
import com.wizzstudio.languagerank.dao.employeerankDAO.EmployeeRankDAO;
import com.wizzstudio.languagerank.dao.employeerankDAO.LanguageCityDAO;
import com.wizzstudio.languagerank.domain.CompanyPost;
import com.wizzstudio.languagerank.domain.CompanySalary;
import com.wizzstudio.languagerank.domain.EmployeeRank;
import com.wizzstudio.languagerank.domain.Language;
import com.wizzstudio.languagerank.dto.EmployeeRankDTO;
import com.wizzstudio.languagerank.service.EmployeeRankService;
import com.wizzstudio.languagerank.service.LanguageTendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
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

    private List<EmployeeRankDTO> employeeRankDTOList = new ArrayList<>();

    private int number = 0;
    private int size = 0;
    private double a = 0.0;
    private Map<String, Integer> map = new HashMap<>();
    private Map<String,Integer> salaryExponentMap = new HashMap<String, Integer>();
    private int m = 0;

    @Override
    public void findOrdPostNumber(List<String> languageName) {

        List<EmployeeRank> employeeRanks = employeeRankDAO.languageEmployeeRank();

//        取排名前十的语言名
        for (EmployeeRank employeeRank : employeeRanks){
            String languageNameRank = employeeRank.getLanguageName();
            size = companyPostDAO.findCompanyPostByLanguageName(languageNameRank).size();

//            取该语言所有公司的岗位数之和，求其平均为a
            List<CompanyPost> companyPosts = companyPostDAO.findCompanyPostByLanguageName(languageNameRank);
            for (CompanyPost companyPost : companyPosts){
                number = companyPost.getCompanyPostNumber();
                map.put(languageNameRank,number);
                a = number + a;
            }
        }
        a = a/size;
    }

    @Override
    public void findSalaryOrd(List<String> languageName) {

        int a = 0;
        List<EmployeeRank> employeeRanks = employeeRankDAO.languageEmployeeRank();
        for (EmployeeRank employeeRank : employeeRanks){

//          获取前十的语言名
            String languageNameRank = employeeRank.getLanguageName();
            List<CompanySalary> companySalaries = companySalaryDAO.findTopFiveByLanguageName(languageNameRank);

//        取前五公司的平均薪资
            for(CompanySalary companySalary : companySalaries){
                a= a + companySalary.getCompanyOrdSalary();
            }
            a = a / 5;
            m = m + a;
            salaryExponentMap.put(languageNameRank,a);
            a = 0;
        }
        m = m / 10;

    }

    @Override
    public Double findSalaryExponent(String languageName) {
        return (double) 50 * salaryExponentMap.get(languageName) / m;
    }

    @Override
    public Double findCityExponent(String languageName) {

        int topSum = 0;
        int allSum = languageCityDAO.findLanguageAllSum(languageName);
        for (int i = 0; i < 5; i++)
            topSum = topSum + languageCityDAO.findLanguageCityTopFiveByLanguageName(languageName).get(i).getCityPostNumber();

        double rate = topSum / allSum;
        return 15 * rate + 5 * (1 - rate);
    }

    @Override
    public Double findLanguagePostNumber(String languageName) {
        return 30 * map.get(languageName) / a;
    }

    @Override
    @Scheduled(cron = "0 0 0 * * 1")
    public void saveExponent() {
        List<Language> languageList = languageDAO.findAll();

        for (Language language : languageList) {
            String temporaryLanguageName = language.getLanguageName();

            Double cityExponent = findCityExponent(temporaryLanguageName);
            Double languagePostNumberExponent = findLanguagePostNumber(temporaryLanguageName);
            Double salaryExponent = findSalaryExponent(temporaryLanguageName);
            Double exponent = cityExponent + languagePostNumberExponent + salaryExponent;

            EmployeeRank employeeRank = new EmployeeRank();

            // 设置小数点后位数
            DecimalFormat df = new DecimalFormat("#.#");
            exponent = Double.parseDouble(df.format(exponent));

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            employeeRank.setLanguageName(temporaryLanguageName);
            employeeRank.setCityPostExponent(cityExponent);
            employeeRank.setLanguagePostExponent(languagePostNumberExponent);
            employeeRank.setSalaryExponent(salaryExponent);
            employeeRank.setEmployeeFinalExponent(exponent);
            try {
                employeeRank.setUpdateTime(dateFormat.parse(dateFormat.format(new Date())));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            employeeRankDAO.save(employeeRank);
        }
    }

    @Override
    public List<EmployeeRankDTO> getEmployeeRank() {

        // 测试用
        saveExponent();


        List<EmployeeRank> employeeRanks = employeeRankDAO.languageEmployeeRank();
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
        return employeeRankDTOList;
    }

}
