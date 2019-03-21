package com.wizzstudio.languagerank.service;

public interface LanguageTendService {

    /**
     * 判断某一种语言的热度发展趋势
     * @param languageName
     * @return LanguageTendEnum
     */
    Integer findLanguageTendNumber(String languageName);

}
