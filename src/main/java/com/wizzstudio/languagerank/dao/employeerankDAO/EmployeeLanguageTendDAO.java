package com.wizzstudio.languagerank.dao.employeerankDAO;

import com.wizzstudio.languagerank.domain.EmployeeRank;
import com.wizzstudio.languagerank.domain.FixedFinalExponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeLanguageTendDAO extends JpaRepository<EmployeeRank, Integer > {


    @Query(nativeQuery = true,value = "select * from employee_rank where language_name = :languageName  " +
            "order by employee_final_exponent DESC limit 2")
    List<EmployeeRank> findByLanguageName(@Param("languageName")String languageName);

}
