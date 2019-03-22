package com.wizzstudio.languagerank.dao.employeerankDAO;

/*
Created by Ben Wen on 2019/3/22.
*/

import com.wizzstudio.languagerank.domain.LanguagePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LanguagePostDAO extends JpaRepository<LanguagePost, Integer> {

    @Query(nativeQuery = true,value = "select * from language_post where language_name = :languageName order by post_number DESC limit 6")
    List<LanguagePost> findLanguagePostByLanguageName(@Param("languageName")String languageName);
}
