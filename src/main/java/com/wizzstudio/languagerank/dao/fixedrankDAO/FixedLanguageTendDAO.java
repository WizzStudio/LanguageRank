package com.wizzstudio.languagerank.dao.fixedrankDAO;

import com.wizzstudio.languagerank.domain.EmployeeRank;
import com.wizzstudio.languagerank.domain.FixedFinalExponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FixedLanguageTendDAO extends JpaRepository<FixedFinalExponent, Integer > {

//    FixedFinalExponent库时间降序排列去前两个，即今天和昨天的数据
    @Query(nativeQuery = true,value = "select * from fixed_final_exponent where language_name = :languageName  " +
            "order by fixed_final_exponent DESC limit 1")
    List<FixedFinalExponent> findByLanguageName(@Param("languageName")String languageName);

}
