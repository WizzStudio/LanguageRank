package com.wizzstudio.languagerank.dao;

import com.wizzstudio.languagerank.domain.CompanySalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalaryExponentDAO extends JpaRepository<CompanySalary, Integer> {

    @Query(nativeQuery = true, value = "select o from CompanySalary o order by o.CompanyOrdSalary DESC limit 5")
    List<CompanySalary> findByLanguageName(String LanguageName);

}
