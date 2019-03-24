package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/3/24.
*/

import com.wizzstudio.languagerank.dao.employeerankDAO.CompanySalaryDAO;
import com.wizzstudio.languagerank.dao.fixedrankDAO.FixedFinalExponentDAO;
import com.wizzstudio.languagerank.domain.CompanySalary;
import com.wizzstudio.languagerank.domain.FixedFinalExponent;
import com.wizzstudio.languagerank.dto.CompanyMaxSalaryDTO;
import com.wizzstudio.languagerank.dto.LanguageHomePageDTO;
import com.wizzstudio.languagerank.service.LanguageHomePageService;
import com.wizzstudio.languagerank.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LanguageHomePageServiceImpl implements LanguageHomePageService {

    @Autowired
    private FixedFinalExponentDAO fixedFinalExponentDAO;

    @Autowired
    private CompanySalaryDAO companySalaryDAO;

    @Autowired
    private LanguageService languageService;

    @Override
    public LanguageHomePageDTO getLanguageHomePage(String languageName) {
        LanguageHomePageDTO languageHomePageDTO = new LanguageHomePageDTO();

        List<FixedFinalExponent> list = fixedFinalExponentDAO.findByLanguageName(languageName);
        languageHomePageDTO.setJoinedNumber(languageService.findJoinedNumberByLanguage(languageName));
        languageHomePageDTO.setFixedFinalExponent(list.get(0).getFixedFinalExponent());
        languageHomePageDTO.setFixedFinalExponentIncreasing(list.get(0).getFixedFinalExponent() - list.get(1).getFixedFinalExponent());

        List<Double> fixedFinalExpontentList = new ArrayList<>();
        for (FixedFinalExponent exponent : fixedFinalExponentDAO.findLastSevenDaysByLanguageName(languageName)) {
            fixedFinalExpontentList.add(exponent.getFixedFinalExponent());
        }
        languageHomePageDTO.setExponentOfLastSevenDays(fixedFinalExpontentList);

        List<CompanySalary> companySalaryList = companySalaryDAO.findTopTwoByLanguageName(languageName);
        CompanyMaxSalaryDTO companyOne = new CompanyMaxSalaryDTO();
        companyOne.setCompanyMaxSalary(companySalaryList.get(0).getCompanyMaxSalary());
        companyOne.setCompanyMaxSalaryPost(companySalaryList.get(0).getCompanyMaxSalaryPost());
        companyOne.setCompanyName(companySalaryList.get(0).getCompanyName());

        CompanyMaxSalaryDTO companyTwo = new CompanyMaxSalaryDTO();
        companyTwo.setCompanyMaxSalary(companySalaryList.get(1).getCompanyMaxSalary());
        companyTwo.setCompanyMaxSalaryPost(companySalaryList.get(1).getCompanyMaxSalaryPost());
        companyTwo.setCompanyName(companySalaryList.get(1).getCompanyName());
        languageHomePageDTO.setCompanyOne(companyOne);
        languageHomePageDTO.setCompanyTwo(companyTwo);

        return languageHomePageDTO;
    }
}
