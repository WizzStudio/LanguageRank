package com.wizzstudio.languagerank.DAO;

/*
Created by Ben Wen on 2019/3/16.
*/

import com.wizzstudio.languagerank.domain.LanguageCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LanguageCountDAO extends JpaRepository<LanguageCount,Integer> {

    LanguageCount findByLanguageName(String languageName);

//    // 更新某种语言的今日新增人数，由aspect调用
//    @Modifying
//    @Query("update LanguageCount o set o.increaseNumber = :increaseNumber where o.languageName = :languageName")
//    void updateIncreaseNumber(@Param("increaseNumber") Integer increaseNumber, @Param("languageName") String languageName);

    // 更新每种语言的总人数，由service调用
    @Modifying
    @Query("update LanguageCount o set o.number = :number where o.languageName = :languageName")
    void updateNumber(@Param("number") Integer number, @Param("languageName") String languageName);

    // 重置某种语言的今日新增人数，由service调用
    @Modifying
    @Query("update LanguageCount o set o.increaseNumber = 0 where o.languageName = :languageName")
    void resetIncreaseNumber(@Param("languageName")String languageName);
}
