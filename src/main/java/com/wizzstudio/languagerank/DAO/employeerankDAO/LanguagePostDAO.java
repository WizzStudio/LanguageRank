package com.wizzstudio.languagerank.DAO.employeerankDAO;

/*
Created by Ben Wen on 2019/3/22.
*/

import com.wizzstudio.languagerank.domain.employeerank.LanguagePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LanguagePostDAO extends JpaRepository<LanguagePost, Integer> {

    // 查询与该语言相关的热门岗位排行前四名
    @Query(nativeQuery = true,value = "select * from language_post where language_name = :languageName order by post_number DESC limit 4")
    List<LanguagePost> findLanguagePostTopFourByLanguageName(@Param("languageName")String languageName);
}
