package com.wizzstudio.languagerank.service;

import org.omg.CORBA.INTERNAL;

import java.util.List;

public interface SalaryOrdService {

//    计算薪资平均值
    Integer findSalaryOrd(List<String> languageName);


}
