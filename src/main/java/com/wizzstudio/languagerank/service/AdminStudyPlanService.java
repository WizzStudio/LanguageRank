package com.wizzstudio.languagerank.service;

import com.wizzstudio.languagerank.dto.AdminStudyPlanDTO;

import java.util.List;

public interface AdminStudyPlanService {

    /**
     * 获取前十语言的学习计划人数
     * @return
     */
    List<AdminStudyPlanDTO> getAdminStudyPlan();

}
