package com.wizzstudio.languagerank.service;

public interface AdminService {

    /**
     * 修改语言热度榜的最终指数
     * @return
     */
    void updateFixedExponent(String languageName, Double FArtificialExponent);

    /**
     * 修改雇主需求榜的最终指数
     * @return
     */
    void updateEmployeeExponent(String languageName, Double EArtificialExponent);

    /**
     * 修改学习计划/最终奖励
     * @return
     */
    String updateStudyPlan();


}
