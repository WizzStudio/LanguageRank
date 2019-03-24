package com.wizzstudio.languagerank.service.impl;


import com.wizzstudio.languagerank.dao.employeerankDAO.EmployeeRankLanguageNameDAO;
import com.wizzstudio.languagerank.domain.EmployeeRank;
import com.wizzstudio.languagerank.service.EmployeeFinalExponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EmployeeFinalExponentServiceImpl implements EmployeeFinalExponentService {

    @Autowired
    CityExponentServiceImpl cityExponentService;
    @Autowired
    PostExponentServiceImpl postExponentService;
    @Autowired
    SalaryExponentServiceImpl salaryExponentService;
    @Autowired
    EmployeeRankLanguageNameDAO employeeRankLanguageNameDAO;

    double exponent = 0.0;

    @Override
    public Double getEmployeeFinalExponent(String languageName) {

        exponent = cityExponentService.findCityExponent(languageName)
                + postExponentService.findLanguagePostNumber(languageName)
                + salaryExponentService.findSalaryExponent(languageName);

        return exponent;
    }

    @Override
    public EmployeeRank saveExponent() {

        EmployeeRank employeeRank = new EmployeeRank();
        Date time = new Date();

        employeeRank.setEmployeeFinalExponent(exponent);
        employeeRank.setUpdateTime(time);
        return employeeRankLanguageNameDAO.save(employeeRank);
    }
}
