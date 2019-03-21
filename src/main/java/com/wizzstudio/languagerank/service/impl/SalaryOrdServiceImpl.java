package com.wizzstudio.languagerank.service.impl;


import com.wizzstudio.languagerank.dao.EmployeeRankDAO;
import com.wizzstudio.languagerank.dao.SalaryExponentDAO;
import com.wizzstudio.languagerank.domain.CompanySalary;
import com.wizzstudio.languagerank.domain.EmployeeRank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalaryOrdServiceImpl {

    @Autowired
    SalaryExponentDAO salaryExponentDAO;

    @Autowired
    EmployeeRankDAO employeeRankDAO;

//    @Override
    public Integer findSalaryOrd(List<String> languageName) {

      int a = 0;
      int i = 0;
      int j = 0;
      int m = 0;
      List<EmployeeRank> employeeRanks = employeeRankDAO.languageEmployeeRank();
      for (EmployeeRank employeeRank : employeeRanks){

          j = j + 1;
          if (j > 9)
              break;

//          获取前十的语言名
          String languageNameRank = employeeRank.getLanguageName();
          List<CompanySalary> companySalaries = salaryExponentDAO.findByLanguageName(languageNameRank);

//        取前五公司的平均薪资
          for(CompanySalary companySalary : companySalaries){
              i = i+1;
              if (i > 4)
                  break;
              a= a + companySalary.getCompanyOrdSalary();
          }
          a = a/5;
          m = m + a;
          a = 0;
      }
      m = m/10;
//      返回前十语言薪资的平均值
      return m;
    }



}
