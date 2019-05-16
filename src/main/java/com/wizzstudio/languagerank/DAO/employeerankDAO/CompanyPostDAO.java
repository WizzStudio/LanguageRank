package com.wizzstudio.languagerank.DAO.employeerankDAO;

/*
Created by Ben Wen on 2019/3/22.
*/

import com.wizzstudio.languagerank.domain.employeerank.CompanyPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyPostDAO extends JpaRepository<CompanyPost, Integer> {

    // 查询使用该语言的公司岗位需求量排行前五名
    @Query(nativeQuery = true, value = "select * from company_post where language_name = :languageName order by company_post_number DESC limit 5")
    List<CompanyPost> findCompanyPostTopFiveByLanguageName(@Param("languageName")String languageName);

    // 查询使用该语言的公司岗位需求量
//    @Query(nativeQuery = true, value = "select * from company_post where language_name = :languageName")
    List<CompanyPost> findCompanyPostByLanguageName(String languageName);
}
