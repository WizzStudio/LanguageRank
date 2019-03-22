package com.wizzstudio.languagerank.dao.employeerankDAO;

import com.wizzstudio.languagerank.domain.CompanySalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanySalaryDAO extends JpaRepository<CompanySalary, Integer> {

    // 查询使用该语言的公司薪资排行前五名
    @Query(nativeQuery = true, value = "select * from company_salary where language_name = :languageName order by company_ord_salary DESC limit 4")
    List<CompanySalary> findByLanguageName(@Param("languageName")String LanguageName);

}
