package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/3/24.
*/

import com.wizzstudio.languagerank.dao.CompanyDAO;
import com.wizzstudio.languagerank.dao.LanguageDAO;
import com.wizzstudio.languagerank.dao.employeerankDAO.CompanySalaryDAO;
import com.wizzstudio.languagerank.dao.fixedrankDAO.FixedFinalExponentDAO;
import com.wizzstudio.languagerank.domain.CompanySalary;
import com.wizzstudio.languagerank.domain.FixedFinalExponent;
import com.wizzstudio.languagerank.dto.CompanyMaxSalaryDTO;
import com.wizzstudio.languagerank.dto.LanguageHomePageDTO;
import com.wizzstudio.languagerank.service.LanguageHomePageService;
import com.wizzstudio.languagerank.service.LanguageCountService;
import com.wizzstudio.languagerank.util.DoubleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LanguageHomePageServiceImpl implements LanguageHomePageService {

    @Autowired
    private FixedFinalExponentDAO fixedFinalExponentDAO;
    @Autowired
    private CompanySalaryDAO companySalaryDAO;
    @Autowired
    private LanguageCountService languageCountService;
    @Autowired
    LanguageDAO languageDAO;
    @Autowired
    CompanyDAO companyDAO;

    private static Map<String, LanguageHomePageDTO> map = new HashMap<>();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetMap() {
        map = new HashMap<>();
    }

    @Override
    public LanguageHomePageDTO getLanguageHomePage(String languageName) {
        if (map.containsKey(languageName)) {
            map.get(languageName).setJoinedNumber(languageCountService.findJoinedNumberByLanguage(languageName));
            return map.get(languageName);
        }

        LanguageHomePageDTO languageHomePageDTO = new LanguageHomePageDTO();

        List<FixedFinalExponent> list = fixedFinalExponentDAO.findTwoByLanguageName(languageName);
        languageHomePageDTO.setLanguageSymbol(languageDAO.findByLanguageName(languageName).getLanguageSymbol());
        languageHomePageDTO.setJoinedNumber(languageCountService.findJoinedNumberByLanguage(languageName));
        languageHomePageDTO.setFixedFinalExponent(list.get(0).getFixedFinalExponent());
        languageHomePageDTO.setLanguageDifficultyIndex(languageDAO.findByLanguageName(languageName).getLanguageDifficultyIndex());
        languageHomePageDTO.setLanguageDevelopmentHistory(languageDAO.findByLanguageName(languageName).getLanguageDevelopmentHistory());
        languageHomePageDTO.setFixedFinalExponentIncreasing(DoubleUtil.getDecimalFormat(list.get(0).getFixedFinalExponent() - list.get(1).getFixedFinalExponent()));
        languageHomePageDTO.setExponentOfLastSevenDays(fixedFinalExponentDAO.findLastSevenDaysByLanguageName(languageName));

        List<CompanySalary> companySalaryList = companySalaryDAO.findTopTwoByLanguageName(languageName);
        List<CompanyMaxSalaryDTO> companyMaxSalaryDTOList = new ArrayList<>();

        CompanyMaxSalaryDTO companyOne = new CompanyMaxSalaryDTO();
        companyOne.setCompanyMaxSalary(companySalaryList.get(0).getCompanyMaxSalary());
        companyOne.setCompanyMaxSalaryPost(companySalaryList.get(0).getCompanyMaxSalaryPost());
        companyOne.setCompanyName(companySalaryList.get(0).getCompanyName());
        companyOne.setCompanySymbol(companyDAO.findByCompanyName(companyOne.getCompanyName()).getCompanySymbol());
        companyMaxSalaryDTOList.add(companyOne);

        CompanyMaxSalaryDTO companyTwo = new CompanyMaxSalaryDTO();
        companyTwo.setCompanyMaxSalary(companySalaryList.get(1).getCompanyMaxSalary());
        companyTwo.setCompanyMaxSalaryPost(companySalaryList.get(1).getCompanyMaxSalaryPost());
        companyTwo.setCompanyName(companySalaryList.get(1).getCompanyName());
        companyTwo.setCompanySymbol(companyDAO.findByCompanyName(companyTwo.getCompanyName()).getCompanySymbol());
        companyMaxSalaryDTOList.add(companyTwo);

        languageHomePageDTO.setCompany(companyMaxSalaryDTOList);

        map.put(languageName, languageHomePageDTO);
        return languageHomePageDTO;
    }
}
