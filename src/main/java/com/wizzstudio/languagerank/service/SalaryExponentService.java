package com.wizzstudio.languagerank.service;

import org.omg.CORBA.INTERNAL;

import java.util.List;

public interface SalaryExponentService {

    /**
     * 计算排名前十语言的平均薪资 m 值
     * @param languageName
     */
    void findSalaryOrd(List<String> languageName);

    /**
     * 计算某一种语言的薪资指数
     * @param languageName
     * @return salaryExponent
     */
    Integer findSalaryExponent(String languageName);


}
