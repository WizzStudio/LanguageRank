package com.wizzstudio.languagerank.service.impl;

import com.wizzstudio.languagerank.DAO.employeerankDAO.EmployeeRankDAO;
import com.wizzstudio.languagerank.DAO.fixedrankDAO.FixedFinalExponentDAO;
import com.wizzstudio.languagerank.domain.employeerank.EmployeeRank;
import com.wizzstudio.languagerank.domain.fixedrank.FixedFinalExponent;
import com.wizzstudio.languagerank.enums.LanguageTendEnum;
import com.wizzstudio.languagerank.service.LanguageTendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageTendServiceImpl implements LanguageTendService {

    @Autowired
    FixedFinalExponentDAO fixedFinalExponentDAO;
    @Autowired
    EmployeeRankDAO employeeRankDAO;

    @Override
    public Integer findFixedLanguageTendNumber(String languageName){

        List<FixedFinalExponent> fixedFinalExponents = fixedFinalExponentDAO.findTwoByLanguageName(languageName);
        double fixedLanguageTendNumber = 0;
        try {
            fixedLanguageTendNumber = fixedFinalExponents.get(0).getFixedFinalExponent()
                    - fixedFinalExponents.get(1).getFixedFinalExponent();
        } catch (IndexOutOfBoundsException e) {
            // 若抛出空指针异常，则为第一天，设置Tend值为不变
            return LanguageTendEnum.NO_CHANGE.getLanguageTend();
        }

        return compareTwoDouble(fixedLanguageTendNumber);
    }

    @Override
    public Integer findEmployeeLanguageTendNumber(String languageName) {
        List<EmployeeRank> employeeRanks = employeeRankDAO.findTwoByLanguageName(languageName);
        double employeeLanguageTendNumber = 0;
        try {
            employeeLanguageTendNumber = employeeRanks.get(0).getEmployeeFinalExponent()
                    - employeeRanks.get(1).getEmployeeFinalExponent();
        } catch (IndexOutOfBoundsException e) {
            // 若抛出空指针异常，则为第一个星期，设置Tend值为不变
            return LanguageTendEnum.NO_CHANGE.getLanguageTend();
        }

        return compareTwoDouble(employeeLanguageTendNumber);
    }

    private Integer compareTwoDouble(Double languageTendNumber) {
        if (languageTendNumber > 0){
            return LanguageTendEnum.INCREASE.getLanguageTend();
        }else if (languageTendNumber < 0){
            return LanguageTendEnum.DECREASE.getLanguageTend();
        }else {
            return LanguageTendEnum.NO_CHANGE.getLanguageTend();
        }
    }

}
