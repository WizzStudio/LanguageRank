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
    Integer findJoinedNumberByLanguage();

    /**
     *查找今日加入学习计划人数
     */
    Integer findJoinedTodayByLanguage();

    /**
     * 更新学习计划人数
     */
    void update(Integer number, String languageName);

    /**
     *查询当前学习语言人数
     */
    LanguageCount findByLanguageName(String languageName);

}
