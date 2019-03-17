package com.wizzstudio.languagerank.service;

/*
Created by Ben Wen on 2019/3/12.
*/

import com.wizzstudio.languagerank.domain.LanguageCount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
     * 每日更新该语言学习总人数
     */
    void updateNumber(Integer increaseNumber, String languageName);

}
