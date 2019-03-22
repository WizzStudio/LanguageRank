package com.wizzstudio.languagerank.dao;

import com.wizzstudio.languagerank.domain.CompanySalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalaryExponentDAO extends JpaRepository<CompanySalary, Integer> {

    @Query(nativeQuery = true, value = "select o from CompanySalary o where o.languageName = :languageName  " +
            "order by o.CompanyOrdSalary DESC limit 4")
    List<CompanySalary> findByLanguageName(@Param("languageName")String languageName);

}
