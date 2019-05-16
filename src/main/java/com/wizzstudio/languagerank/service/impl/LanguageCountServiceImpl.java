package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/3/12.
*/

import com.wizzstudio.languagerank.DAO.LanguageCountDAO;
import com.wizzstudio.languagerank.domain.LanguageCount;
import com.wizzstudio.languagerank.service.LanguageCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LanguageCountServiceImpl implements LanguageCountService {

    @Autowired
    LanguageCountDAO languageCountDAO;

    // 第一项为加入总人数，第二项为今日新增人数
    @Override
    public Integer[] findJoinedNumberByLanguage(String languageName) {
        LanguageCount languageCount = languageCountDAO.findByLanguageName(languageName);
        return new Integer[]{languageCount.getNumber() + languageCount.getIncreaseNumber(), languageCount.getIncreaseNumber()};
    }
//
//    @Override
//    public Integer findJoinedTodayByLanguage(String languageName) {
//        return languageCountDAO.findByLanguageName(languageName).getIncreaseNumber();
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateNumber() {
        List<LanguageCount> languageCountDAOList = languageCountDAO.findAll();
        for (LanguageCount languageCount : languageCountDAOList) {
            String languageName = languageCount.getLanguageName();
            Integer number = findJoinedNumberByLanguage(languageName)[0];

            languageCountDAO.updateNumber(number,languageName);
            languageCountDAO.resetIncreaseNumber(languageName);
        }
    }
}
