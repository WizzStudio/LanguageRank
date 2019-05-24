package com.wizzstudio.languagerank.service.Admin;

import com.wizzstudio.languagerank.DTO.admin.AdminStudyPlanDTO;

import java.util.List;

/**
 * 暂时不需要
 */
public interface AdminStudyPlanService {

    /**
     * 获取前十语言的学习计划人数
     * @return
     */
    List<AdminStudyPlanDTO> getAdminStudyPlan();

}
