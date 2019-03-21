package com.wizzstudio.languagerank.service.impl;

import com.wizzstudio.languagerank.dao.SalaryExponentDAO;
import com.wizzstudio.languagerank.domain.CompanySalary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryExponentServiceImpl {

    @Autowired
    SalaryExponentDAO salaryExponentDAO;
    SalaryOrdServiceImpl salaryOrdService;

    //    @Override
    public Integer findSalaryExponent(String languageName){

        int i = 0;
        int a = 0;
        List<CompanySalary> companySalaries = salaryExponentDAO.findByLanguageName(languageName);

//        取某语言前五公司的平均薪资
        for(CompanySalary companySalary : companySalaries){
            i = i+1;
            if (i > 4)
                break;
            a= a + companySalary.getCompanyOrdSalary();
        }
        a = a/5;
        Integer SalaryExponent = 50*a/salaryOrdService.findSalaryOrd();
        return SalaryExponent;
    }


}
