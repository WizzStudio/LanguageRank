package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/4/9.
*/

import com.wizzstudio.languagerank.dao.AwardDAO;
import com.wizzstudio.languagerank.domain.Award;
import com.wizzstudio.languagerank.service.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AwardServiceImpl implements AwardService {
    @Autowired
    private AwardDAO awardDAO;

    @Override
    public Award findAwardByLanguageName(String languageName) {
        return awardDAO.findByLanguageName(languageName);
    }

    // 更新award的后台管理接口还没写
}
