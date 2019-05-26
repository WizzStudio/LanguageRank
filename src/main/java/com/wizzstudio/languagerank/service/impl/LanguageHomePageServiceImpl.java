package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/3/24.
*/

import com.wizzstudio.languagerank.DAO.CompanyDAO;
import com.wizzstudio.languagerank.DAO.GithubPopularProjectDAO;
import com.wizzstudio.languagerank.DAO.LanguageDAO;
import com.wizzstudio.languagerank.DAO.employeerankDAO.CompanySalaryDAO;
import com.wizzstudio.languagerank.DAO.fixedrankDAO.FixedFinalExponentDAO;
import com.wizzstudio.languagerank.DTO.CompanyMaxSalaryDTO;
import com.wizzstudio.languagerank.VO.LanguageHomePageVO;
import com.wizzstudio.languagerank.domain.employeerank.CompanySalary;
import com.wizzstudio.languagerank.domain.fixedrank.FixedFinalExponent;
import com.wizzstudio.languagerank.service.LanguageHomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    LanguageDAO languageDAO;
    @Autowired
    CompanyDAO companyDAO;
    @Autowired
    GithubPopularProjectDAO githubPopularProjectDAO;

    private static Map<String, LanguageHomePageVO> map = new HashMap<>();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetMap() {
        map = new HashMap<>();
    }

    @Override
    public LanguageHomePageVO getLanguageHomePage(String languageName) {
        if (map.containsKey(languageName)) {
//            map.get(languageName).setJoinedNumber(languageCountService.findJoinedNumberByLanguage(languageName)[0]);
            return map.get(languageName);
        }

        LanguageHomePageVO languageHomePageVO = new LanguageHomePageVO();

        List<FixedFinalExponent> list = fixedFinalExponentDAO.findTwoByLanguageName(languageName);
//        languageHomePageVO.setLanguageSymbol(languageDAO.findByLanguageName(languageName).getLanguageSymbol());
//        languageHomePageVO.setJoinedNumber(languageCountService.findJoinedNumberByLanguage(languageName)[0]);
//        languageHomePageVO.setFixedFinalExponent(list.get(0).getFixedFinalExponent());
//        languageHomePageVO.setLanguageDifficultyIndex(languageDAO.findByLanguageName(languageName).getLanguageDifficultyIndex());
//        languageHomePageVO.setLanguageDevelopmentHistory(languageDAO.findByLanguageName(languageName).getLanguageDevelopmentHistory());
//        languageHomePageVO.setFixedFinalExponentIncreasing(DoubleUtil.getDecimalFormat(list.get(0).getFixedFinalExponent() - list.get(1).getFixedFinalExponent()));
        languageHomePageVO.setExponentOfLastSevenDays(fixedFinalExponentDAO.findLastSevenDaysByLanguageName(languageName));

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

        languageHomePageVO.setCompany(companyMaxSalaryDTOList);
        languageHomePageVO.setGithubPopularProjectList(githubPopularProjectDAO.findByProjectTag(languageName));

        map.put(languageName, languageHomePageVO);
        return languageHomePageVO;
    }
}
