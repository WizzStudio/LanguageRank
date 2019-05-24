package com.wizzstudio.languagerank.DAO;

/*
Created by Ben Wen on 2019/4/5.
*/

import com.wizzstudio.languagerank.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CompanyDAO extends JpaRepository<Company, Integer> {
    Company findByCompanyName(String companyName);
}
