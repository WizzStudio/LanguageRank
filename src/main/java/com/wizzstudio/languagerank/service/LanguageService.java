package com.wizzstudio.languagerank.service;

/*
Created by Ben Wen on 2019/3/12.
*/

public interface LanguageService {
    /**
     *查找已加入学习计划人数
     */
    Integer findJoinedNumberByLanguage();

    /**
     *查找今日加入学习计划人数
     */
    Integer findJoinedTodayByLanguage();
}
