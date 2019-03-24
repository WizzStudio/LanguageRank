package com.wizzstudio.languagerank.service;

import com.wizzstudio.languagerank.domain.EmployeeRank;

public interface EmployeeFinalExponentService {

    /**
     * 计算雇主榜最终指数
     * @param languageName
     * @return employeefinalexponent
     */
    Double getEmployeeFinalExponent(String languageName);

    EmployeeRank saveExponent();

}
