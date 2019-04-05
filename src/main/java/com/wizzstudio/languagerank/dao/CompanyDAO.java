package com.wizzstudio.languagerank.dao;

/*
Created by Ben Wen on 2019/4/5.
*/

import com.wizzstudio.languagerank.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyDAO extends JpaRepository<Company, Integer> {
    Company findByCompanyName(String companyName);
}
