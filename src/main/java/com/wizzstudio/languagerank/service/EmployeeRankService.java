package com.wizzstudio.languagerank.service;

import com.wizzstudio.languagerank.VO.EmployeeRankVO;

import java.util.List;

public interface EmployeeRankService {

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
    List<EmployeeRankVO> getEmployeeRank();

    /**
     * 每周更新一次list（缓存用）
     */
    void resetList();

}
