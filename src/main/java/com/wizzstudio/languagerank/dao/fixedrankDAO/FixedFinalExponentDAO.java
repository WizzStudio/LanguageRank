package com.wizzstudio.languagerank.dao.fixedrankDAO;

/*
Created by Ben Wen on 2019/3/24.
*/

import com.wizzstudio.languagerank.domain.FixedFinalExponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FixedFinalExponentDAO extends JpaRepository<FixedFinalExponent, Integer> {

    // FixedFinalExponent库时间降序排列去前两个，即今天和昨天的数据
    @Query(nativeQuery = true,value = "select * from fixed_final_exponent where language_name = :languageName  " +
            "order by update_time DESC limit 2")
    List<FixedFinalExponent> findTwoByLanguageName(@Param("languageName")String languageName);

    @Query(nativeQuery = true,value = "select * from fixed_final_exponent where language_name = :languageName  " +
            "order by update_time DESC limit 7")
    List<FixedFinalExponent> findLastSevenDaysByLanguageName(@Param("languageName")String languageName);

    // 排序语言热度最终指数前十的语言
    @Query(nativeQuery = true,value = "select * from fixed_final_exponent where update_time =" +
            " (select update_time from fixed_final_exponent order by update_time DESC limit 1)" +
            " order by fixed_final_exponent DESC limit 10")
    List<FixedFinalExponent> languageFixedRank();

}
