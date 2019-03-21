package com.wizzstudio.languagerank.dao;

import com.wizzstudio.languagerank.domain.EmployeeRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRankDAO extends JpaRepository<EmployeeRank, Integer> {

//      排序前十语言
    @Query(nativeQuery = true,value = "select o from  EmployeeRank  o order by o.employeeFinalExponent ASC limit 10")
    List<EmployeeRank> languageEmployeeRank();

}
