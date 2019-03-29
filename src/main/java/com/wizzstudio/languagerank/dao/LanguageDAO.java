package com.wizzstudio.languagerank.dao;

/*
Created by Ben Wen on 2019/3/29.
*/

import com.wizzstudio.languagerank.domain.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageDAO extends JpaRepository<Language, Integer> {
    Language findByLanguageName(String languageName);
}
