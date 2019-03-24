package com.wizzstudio.languagerank.service.impl;

import com.wizzstudio.languagerank.dao.fixedrankDAO.FixedLanguageTendDAO;
import com.wizzstudio.languagerank.dao.employeerankDAO.EmployeeLanguageTend;
import com.wizzstudio.languagerank.domain.EmployeeRank;
import com.wizzstudio.languagerank.domain.FixedFinalExponent;
import com.wizzstudio.languagerank.enums.LanguageTendEnum;
import com.wizzstudio.languagerank.service.LanguageTendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageTendServiceImpl implements LanguageTendService {

    @Autowired
    FixedLanguageTendDAO languageTendDAO;
    @Autowired
    EmployeeLanguageTend employeeLanguageTend;

    @Override
    public Integer findFixedLanguageTendNumber(String languageName){

        List<FixedFinalExponent> fixedFinalExponents = languageTendDAO.findByLanguageName(languageName);
        double fixedLanguageTendNumber = fixedFinalExponents.get(1).getFixedFinalExponent()
                - fixedFinalExponents.get(2).getFixedFinalExponent();

        if (fixedLanguageTendNumber > 0){
            return LanguageTendEnum.INCREASE.getLanguageTend();
        }else if (fixedLanguageTendNumber < 0){
            return LanguageTendEnum.DECREASE.getLanguageTend();
        }else
            return LanguageTendEnum.NO_CHANGE.getLanguageTend();
    }

    @Override
    public Integer findEmployeeLanguageTendNumber(String languageName) {
        List<EmployeeRank> employeeRanks = employeeLanguageTend.findByLanguageName(languageName);
        double employeeLanguageTendNumber = employeeRanks.get(1).getEmployeeFinalExponent()
                - employeeRanks.get(2).getEmployeeFinalExponent();

        if (employeeLanguageTendNumber > 0){
            return LanguageTendEnum.INCREASE.getLanguageTend();
        }else if (employeeLanguageTendNumber < 0){
            return LanguageTendEnum.DECREASE.getLanguageTend();
        }else
            return LanguageTendEnum.NO_CHANGE.getLanguageTend();
    }

}
