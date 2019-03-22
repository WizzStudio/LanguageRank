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

    // 查询有该语言相关岗位的所有城市
    @Query(nativeQuery = true, value = "select * from language_city where language_name = :languageName")
    List<LanguageCity> findLanguageCityByLanguageName(@Param("languageName")String LanguageName);

    // 查询该语言相关岗位数前五的城市
    @Query(nativeQuery = true, value = "select * from language_city where language_name = :languageName order by city_post_number DESC limit 4")
    List<LanguageCity> findLanguageCityTopFiveByLanguageName(@Param("languageName")String LanguageName);

    // 由数据库计算该语言的全部岗位数
    @Query(nativeQuery = true, value = "select sum(city_post_number) from language_city where language_name = :languageName")
    Integer findLanguageAllSum(@Param("languageName" )String languageName);
}
