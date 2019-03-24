package com.wizzstudio.languagerank.dao.fixedrankDAO;

import com.wizzstudio.languagerank.domain.EmployeeRank;
import com.wizzstudio.languagerank.domain.FixedFinalExponent;
import com.wizzstudio.languagerank.domain.FixedRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FixedRankLanguageNameDAO extends JpaRepository<FixedFinalExponent, Integer> {

    // 排序前十语言
    @Query(nativeQuery = true,value = "select * from fixed_final_exponent order by fixed_final_exponent DESC limit 9")
    List<FixedFinalExponent> languageFixedRank();
}
