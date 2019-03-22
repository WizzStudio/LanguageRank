package com.wizzstudio.languagerank.dao.employeerankDAO;

/*
Created by Ben Wen on 2019/3/22.
*/

import com.wizzstudio.languagerank.domain.LanguageCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LanguageCityDAO extends JpaRepository<LanguageCity, Integer> {

    @Query(nativeQuery = true, value = "select * from language_city where language_name = :languageName")
    List<LanguageCity> findLanguageCityByLanguageName(@Param("languageName")String LanguageName);
}
