package com.wizzstudio.languagerank.service;


public interface LanguageService {
    /**
     *查找已加入学习计划人数
     */
    Integer findJoinedNumberByLanguage(String languageName);

    /**
     *查找今日加入学习计划人数
     */
    Integer findJoinedTodayByLanguage(String languageName);

    /**
     * 每日更新每种语言学习的总人数，并重置每日新增人数
     */
    void updateNumber();

}
