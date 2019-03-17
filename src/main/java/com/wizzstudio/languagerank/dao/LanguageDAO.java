package com.wizzstudio.languagerank.dao;

/*
Created by Ben Wen on 2019/3/16.
*/

import com.wizzstudio.languagerank.domain.LanguageCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LanguageDAO extends JpaRepository<LanguageCount,Integer> {

    LanguageCount findByLanguageName(String languageName);

    // 更新新增人数，由aspect调用
    @Modifying
    @Query("update LanguageCount o set o.increaseNumber = :increaseNumber where o.languageName = :languageName")
    void updateIncreaseNumber(@Param("increaseNumber")Integer increaseNumber, @Param("languageName")String languageName);

    // 更新总人数，由service调用

}
