package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/4/5.
*/

import com.wizzstudio.languagerank.dao.LanguageDAO;
import com.wizzstudio.languagerank.domain.Company;
import com.wizzstudio.languagerank.dto.MoreLanguageInformationDTO;
import com.wizzstudio.languagerank.service.LanguageMoreInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LanguageMoreInformationServiceImpl implements LanguageMoreInformationService {
    @Autowired
    private LanguageDAO languageDAO;

    @Override
    public MoreLanguageInformationDTO getMoreLanguageInformation(String languageName) {
        MoreLanguageInformationDTO moreLanguageInformation = new MoreLanguageInformationDTO();
        moreLanguageInformation.setLanguage(languageDAO.findByLanguageName(languageName));
        switch (languageName) {
            case "Java":
//                moreLanguageInformation.setCompanyList();
            case "Python":
            case "C":
            case "C++":
            case "JavaScript":
            case "PHP":
            case "C#":
            case "Scala":
            case "SQL":
            case "Objective-C":
            case "R":
            case "Go":
            case "Matlab":
            case "Assembly":
            case "Ruby":
        }

        return moreLanguageInformation;
    }
}
