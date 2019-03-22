package com.wizzstudio.languagerank.dao;

import com.wizzstudio.languagerank.domain.LanguageCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CityExponentDAO extends JpaRepository<LanguageCity, Integer> {

//      查询岗位数前五的城市
    @Query(nativeQuery = true,value = "select o from LanguageCity o where o.languageName = :languageName " +
            "order by o.postNumber limit 4")
    List<LanguageCity> findLanguageTopSum(@Param("languageName" )String languageName);

//      由数据库计算该语言的全部岗位数
    @Query(nativeQuery = true, value = "select sum(postNumber) from LanguageCity where o.languageName = :languageName")
    Integer findLanguageAllSum(@Param("languageName" )String languageName);

}
