package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/3/12.
*/

import com.wizzstudio.languagerank.dao.LanguageCountDAO;
import com.wizzstudio.languagerank.domain.LanguageCount;
import com.wizzstudio.languagerank.service.LanguageCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LanguageCountServiceImpl implements LanguageCountService {

    @Autowired
    LanguageCountDAO languageCountDAO;

    // 当前总人数为number与increaseNumber之和
    @Override
    public Integer findJoinedNumberByLanguage(String languageName) {
        return languageCountDAO.findByLanguageName(languageName).getNumber()+ languageCountDAO.findByLanguageName(languageName).getIncreaseNumber();
    }

    @Override
    public Integer findJoinedTodayByLanguage(String languageName) {
        return languageCountDAO.findByLanguageName(languageName).getIncreaseNumber();
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void updateNumber() {
        List<LanguageCount> languageCountDAOList = languageCountDAO.findAll();
        for (LanguageCount languageCount : languageCountDAOList) {
            String languageName = languageCount.getLanguageName();
            Integer number = findJoinedNumberByLanguage(languageName);

            languageCountDAO.updateNumber(number,languageName);
            languageCountDAO.resetIncreaseNumber(languageName);
        }
    }
}
