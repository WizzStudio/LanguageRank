package com.wizzstudio.languagerank.service;

import java.util.List;

public interface PostExponentService {

    /**
     * 计算排名前十语言的平均需求量，即a值
     * @param languageName
     */
    void findOrdPostNumber(List<String> languageName);

    /**
     * 计算雇主需求榜需求量指数，即EmployeeRank表里的languagePostExponent
     * @param languageName
     * @return languagePostExponent
     */
    Double findLanguagePostNumber(String languageName);

}
