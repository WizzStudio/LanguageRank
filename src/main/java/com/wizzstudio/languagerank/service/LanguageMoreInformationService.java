package com.wizzstudio.languagerank.service;

/*
Created by Ben Wen on 2019/4/5.
*/

import com.wizzstudio.languagerank.dto.MoreLanguageInformationDTO;

public interface LanguageMoreInformationService {
    /**
     *  组装语言介绍页信息并返回
     */
    MoreLanguageInformationDTO getMoreLanguageInformation(String languageName);
}
