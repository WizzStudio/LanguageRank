package com.wizzstudio.languagerank.service;

import com.wizzstudio.languagerank.domain.EmployeeRank;
import com.wizzstudio.languagerank.dto.EmployeeRankDTO;

import java.util.List;

public interface EmployeeRankService {

    /**
     * 计算排名前十语言的平均需求量，即a值
     */
    void findOrdPostNumber(List<String> languageName);

    /**
     * 计算排名前十语言的平均薪资 m 值
     */
    void findSalaryOrd(List<String> languageName);

    /**
     * 计算某一种语言的薪资指数
     * @return salaryExponent
     */
    Double findSalaryExponent(String languageName);

    /**
     * 计算雇主需求榜的城市指数
     * @return CityExponent
     */
    Double findCityExponent(String languageName);

    /**
     * 计算雇主需求榜需求量指数，即EmployeeRank表里的languagePostExponent
     * @return languagePostExponent
     */
    Double findLanguagePostNumber(String languageName);

    /**
     * 每周一零点计算并保存雇主需求榜最终指数
     */
    void saveExponent();

    /**
     * 获取雇主需求榜的四个字段
     */
    List<EmployeeRankDTO> getEmployeeRank();

}
