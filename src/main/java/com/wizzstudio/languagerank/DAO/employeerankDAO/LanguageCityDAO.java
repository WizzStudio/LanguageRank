package com.wizzstudio.languagerank.DAO.employeerankDAO;

/*
Created by Ben Wen on 2019/3/22.
*/

import com.wizzstudio.languagerank.domain.employeerank.LanguageCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LanguageCityDAO extends JpaRepository<LanguageCity, Integer> {

    // 查询有该语言相关岗位的前五之外的城市
    @Query(nativeQuery = true, value = "select * from language_city where language_name = :languageName order by city_post_number DESC limit 5,45")
    List<LanguageCity> findLanguageCityOutOfFiveByLanguageName(@Param("languageName")String languagename);

    // 查询该语言相关岗位数前五的城市
    @Query(nativeQuery = true, value = "select * from language_city where language_name = :languageName order by city_post_number DESC limit 5")
    List<LanguageCity> findLanguageCityTopFiveByLanguageName(@Param("languageName")String languagename);

    // 由数据库计算该语言的全部岗位数
    @Query(nativeQuery = true, value = "select sum(city_post_number) from language_city where language_name = :languageName")
    Integer findLanguageAllSum(@Param("languageName" )String languageName);
}

