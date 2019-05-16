package com.wizzstudio.languagerank.service;

/*
Created by Ben Wen on 2019/3/24.
*/

import com.wizzstudio.languagerank.VO.LanguageHomePageVO;

public interface LanguageHomePageService {
    /**
     * 每日更新一次map（缓存用）
     */
    void resetMap();

    /**
     * 获取语言主页
     */
    LanguageHomePageVO getLanguageHomePage(String languageName);
}
