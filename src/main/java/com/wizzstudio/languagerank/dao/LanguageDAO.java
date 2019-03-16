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

//    @Modifying
//    @Query("update LanguageCount o set o.number = :number where o.languageName = :languageName")
//    void update(@Param("number")Integer number, @Param("languageName")String languageName);
}
