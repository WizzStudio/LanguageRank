package com.wizzstudio.languagerank.dao;

/*
Created by Ben Wen on 2019/3/26.
*/

import com.wizzstudio.languagerank.domain.UserStudyedLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserStudyedLanguageDAO extends JpaRepository<UserStudyedLanguage, Integer> {
    @Query("select u from UserStudyedLanguage u where u.userId = :userId")
    List<UserStudyedLanguage> findStudyedLanguageByUserId(@Param("userId") Integer userId);
}
