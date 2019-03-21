package com.wizzstudio.languagerank.dao;

import com.wizzstudio.languagerank.domain.CompanySalary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaryExponentDAO extends JpaRepository<CompanySalary, Integer> {

    List<CompanySalary> findByLanguageName(String LanguageName);

}
