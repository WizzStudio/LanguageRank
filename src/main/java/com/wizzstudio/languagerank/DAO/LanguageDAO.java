package com.wizzstudio.languagerank.DAO;

/*
Created by Ben Wen on 2019/3/29.
*/

import com.wizzstudio.languagerank.domain.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LanguageDAO extends JpaRepository<Language, Integer> {
    Language findByLanguageName(String languageName);

    @Query("select l.languageSymbol from Language l where l.languageName = :languageName")
    String findLanguageSymbolByLanguageName(@Param("languageName") String languageName);
}
