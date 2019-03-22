package com.wizzstudio.languagerank.dao.employeerankDAO;

import com.wizzstudio.languagerank.domain.EmployeeRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRankDAO extends JpaRepository<EmployeeRank, Integer> {

    // 排序前十语言
    @Query(nativeQuery = true,value = "select * from employee_rank order by employee_final_exponent DESC limit 9")
    List<EmployeeRank> languageEmployeeRank();
}
