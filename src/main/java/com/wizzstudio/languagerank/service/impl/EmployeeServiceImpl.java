package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/3/22.
*/

import com.wizzstudio.languagerank.DAO.employeerankDAO.CompanyPostDAO;
import com.wizzstudio.languagerank.DAO.employeerankDAO.CompanySalaryDAO;
import com.wizzstudio.languagerank.DAO.employeerankDAO.LanguageCityDAO;
import com.wizzstudio.languagerank.DAO.employeerankDAO.LanguagePostDAO;
import com.wizzstudio.languagerank.domain.employeerank.CompanyPost;
import com.wizzstudio.languagerank.domain.employeerank.CompanySalary;
import com.wizzstudio.languagerank.domain.employeerank.LanguageCity;
import com.wizzstudio.languagerank.domain.employeerank.LanguagePost;
import com.wizzstudio.languagerank.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private LanguagePostDAO languagePostDAO;
    @Autowired
    private CompanySalaryDAO companySalaryDAO;
    @Autowired
    private CompanyPostDAO companyPostDAO;
    @Autowired
    private LanguageCityDAO languageCityDAO;

    private static Map<String, List<LanguagePost>> languagePostMap = new HashMap<>();
    private static Map<String, List<CompanySalary>> companySalaryMap = new HashMap<>();
    private static Map<String, List<CompanyPost>> companyPostMap = new HashMap<>();
    private static Map<String, List<LanguageCity>> languageCityMap = new HashMap<>();

    @Override
    public List<LanguagePost> getLanguagePost(String languageName) {
        if (!languagePostMap.containsKey(languageName)) {
            languagePostMap.put(languageName, languagePostDAO.findLanguagePostTopFourByLanguageName(languageName));
        }
        return languagePostMap.get(languageName);
    }

    @Override
    public List<CompanySalary> getCompanySalary(String languageName) {
        if (!companySalaryMap.containsKey(languageName)) {
            companySalaryMap.put(languageName, companySalaryDAO.findTopFiveByLanguageName(languageName));
        }
        return companySalaryMap.get(languageName);
    }

    @Override
    public List<CompanyPost> getCompanyPost(String languageName) {
        if (!companyPostMap.containsKey(languageName)) {
            companyPostMap.put(languageName, companyPostDAO.findCompanyPostTopFiveByLanguageName(languageName));
        }
        return companyPostMap.get(languageName);
    }

    @Override
    public List<LanguageCity> getLanguageCity(String languageName) {
        if (!languageCityMap.containsKey(languageName)) {
            List<LanguageCity> languageCityList = languageCityDAO.findLanguageCityTopFiveByLanguageName(languageName);
            List<LanguageCity> languageCityTemporaryList = languageCityDAO.findLanguageCityOutOfFiveByLanguageName(languageName);
            int otherCityPostNumber = 0;
            for (LanguageCity languageCity : languageCityTemporaryList) {
                otherCityPostNumber += languageCity.getCityPostNumber();
            }
            LanguageCity languageCity = new LanguageCity();
            languageCity.setLanguageName(languageName);
            languageCity.setLanguageCity("其它");
            languageCity.setCityPostNumber(otherCityPostNumber);

            languageCityList.add(languageCity);
            languageCityMap.put(languageName, languageCityList);
        }
        return languageCityMap.get(languageName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetList() {
        languagePostMap = new HashMap<>();
        companySalaryMap = new HashMap<>();
        companyPostMap = new HashMap<>();
        languageCityMap = new HashMap<>();
    }
}
