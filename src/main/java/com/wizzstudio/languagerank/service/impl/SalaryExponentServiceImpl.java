package com.wizzstudio.languagerank.service.impl;


import com.wizzstudio.languagerank.dao.employeerankDAO.EmployeeRankLanguageNameDAO;
import com.wizzstudio.languagerank.dao.employeerankDAO.CompanySalaryDAO;
import com.wizzstudio.languagerank.domain.CompanySalary;
import com.wizzstudio.languagerank.domain.EmployeeRank;
import com.wizzstudio.languagerank.service.SalaryExponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SalaryExponentServiceImpl implements SalaryExponentService {

    @Autowired
    CompanySalaryDAO companySalaryDAO;

    @Autowired
    EmployeeRankLanguageNameDAO employeeRankDAO;

    Map<String,Integer> map = new HashMap<String, Integer>();
    int m = 0;

    @Override
    public void findSalaryOrd(List<String> languageName) {

      int a = 0;
        List<EmployeeRank> employeeRanks = employeeRankDAO.languageEmployeeRank();
        for (EmployeeRank employeeRank : employeeRanks){

//          获取前十的语言名
          String languageNameRank = employeeRank.getLanguageName();
          List<CompanySalary> companySalaries = companySalaryDAO.findTopFiveByLanguageName(languageNameRank);

//        取前五公司的平均薪资
          for(CompanySalary companySalary : companySalaries){
              a= a + companySalary.getCompanyOrdSalary();
          }
          a = a/5;
          m = m + a;
          map.put(languageNameRank,a);
          a = 0;
      }
      m = m/10;

    }

    @Override
    public Integer findSalaryExponent(String languageName) {
        int salaryExponent =  50 * map.get(languageName) / m;
        return salaryExponent;
    }

}
