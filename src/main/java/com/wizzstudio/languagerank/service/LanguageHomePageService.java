package com.wizzstudio.languagerank.service;

/*
Created by Ben Wen on 2019/3/24.
*/

import com.wizzstudio.languagerank.dto.LanguageHomePageDTO;

public interface LanguageHomePageService {
    /**
     * 获取语言主页
     */
    LanguageHomePageDTO getLanguageHomePage(String languageName);
}
