package com.wizzstudio.languagerank.service.Admin;

/**
 * 暂时不需要
 */
public interface AdminExponentService {

    /**
     * 修改语言热度榜的最终指数
     * @return
     */
    void updateFixedExponent(String languageName, Double Fartificialexponent);

    /**
     * 修改雇主需求榜的最终指数
     * @return
     */
    void updateEmployeeExponent(String languageName, Double Eartificialexponent);

    /**
     * 修改学习计划/最终奖励
     * @return
     */
    String updateStudyPlan();


}
