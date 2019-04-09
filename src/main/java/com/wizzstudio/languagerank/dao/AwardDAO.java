package com.wizzstudio.languagerank.dao;

/*
Created by Ben Wen on 2019/4/9.
*/

import com.wizzstudio.languagerank.domain.Award;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AwardDAO extends JpaRepository<Award, Integer> {
    Award findByLanguageName(String languageName);
}
