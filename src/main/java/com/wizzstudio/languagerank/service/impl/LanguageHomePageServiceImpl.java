package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/3/24.
*/

import com.wizzstudio.languagerank.dao.fixedrankDAO.FixedFinalExponentDAO;
import com.wizzstudio.languagerank.domain.FixedFinalExponent;
import com.wizzstudio.languagerank.dto.LanguageHomePageDTO;
import com.wizzstudio.languagerank.service.LanguageHomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageHomePageServiceImpl implements LanguageHomePageService {

    @Autowired
    private FixedFinalExponentDAO fixedFinalExponentDAO;

    @Override
    public LanguageHomePageDTO getLanguageHomePage(String languageName) {
        List<FixedFinalExponent> list = fixedFinalExponentDAO.findByLanguageName(languageName);
        return null;
    }
}
