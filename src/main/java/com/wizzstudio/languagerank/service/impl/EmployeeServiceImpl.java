package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/3/22.
*/

import com.wizzstudio.languagerank.dao.employeerankDAO.CompanyPostDAO;
import com.wizzstudio.languagerank.dao.employeerankDAO.CompanySalaryDAO;
import com.wizzstudio.languagerank.dao.employeerankDAO.LanguageCityDAO;
import com.wizzstudio.languagerank.dao.employeerankDAO.LanguagePostDAO;
import com.wizzstudio.languagerank.domain.CompanyPost;
import com.wizzstudio.languagerank.domain.CompanySalary;
import com.wizzstudio.languagerank.domain.LanguageCity;
import com.wizzstudio.languagerank.domain.LanguagePost;
import com.wizzstudio.languagerank.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    private static List<LanguagePost> languagePostList = new ArrayList<>();
    private static List<CompanySalary> companySalaryList = new ArrayList<>();
    private static List<CompanyPost> companyPostList = new ArrayList<>();
    private static List<LanguageCity> languageCityList = new ArrayList<>();

    @Override
    public List<LanguagePost> getLanguagePost(String languageName) {
        if (languagePostList.isEmpty()) {
            languagePostList = languagePostDAO.findLanguagePostByLanguageName(languageName);
        }
        return languagePostList;
    }

    @Override
    public List<CompanySalary> getCompanySalary(String languageName) {
        if (companySalaryList.isEmpty()) {
            companySalaryList = companySalaryDAO.findTopFiveByLanguageName(languageName);
        }
        return companySalaryList;
    }

    @Override
    public List<CompanyPost> getCompanyPost(String languageName) {
        if (companyPostList.isEmpty()) {
            companyPostList = companyPostDAO.findCompanyPostTopFiveByLanguageName(languageName);
        }
        return companyPostList;
    }

    @Override
    public List<LanguageCity> getLanguageCity(String languageName) {
        if (languageCityList.isEmpty()) {
            languageCityList = languageCityDAO.findLanguageCityByLanguageName(languageName);
        }
        return languageCityList;
    }

    @Override
    @Scheduled(cron = "0 1 0 * * 1")
    @Transactional(rollbackFor = Exception.class)
    public void resetList() {
        languagePostList = new ArrayList<>();
        companySalaryList = new ArrayList<>();
        companyPostList = new ArrayList<>();
        languageCityList = new ArrayList<>();
    }
}
