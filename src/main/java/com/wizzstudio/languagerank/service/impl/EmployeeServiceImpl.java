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
import org.springframework.stereotype.Service;

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

    @Override
    public List<LanguagePost> getLanguagePost(String languageName) {
        return languagePostDAO.findLanguagePostByLanguageName(languageName);
    }

    @Override
    public List<CompanySalary> getCompanySalary(String languageName) {
        return companySalaryDAO.findTopFiveByLanguageName(languageName);
    }

    @Override
    public List<CompanyPost> getCompanyPost(String languageName) {
        return companyPostDAO.findCompanyPostTopFiveByLanguageName(languageName);
    }

    @Override
    public List<LanguageCity> getLanguageCity(String languageName) {
        return languageCityDAO.findLanguageCityByLanguageName(languageName);
    }
}
