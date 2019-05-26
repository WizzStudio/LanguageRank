package com.wizzstudio.languagerank.service.Admin;

import com.wizzstudio.languagerank.DTO.admin.AdminTotalDTO;
import com.wizzstudio.languagerank.VO.AdminProjectListVO;
import com.wizzstudio.languagerank.VO.AdminProjectStudyPlanVO;

public interface AdminProjectService {

    /**
     * 获得班级学习，名称、人数、讨论数
     */
    AdminProjectListVO getAllProject(Integer page, Integer size);

    /**
     * 获取班级的人数、讨论数、膜拜数（暂时无）
     */
    AdminTotalDTO getProjectTotalNumber(Integer id);

    /**
     * 获取班级的基本信息及学习计划
     */
    AdminProjectStudyPlanVO getAdminProjectPlan(Integer id);

    /**
     * 更新班级信息，即学习计划
     */
    void saveAdminProjectStudyPlanVO(AdminProjectStudyPlanVO adminProjectStudyPlanVO,Integer clazzId);

    /**
     * 删除班级、学习计划
     */
    void deleteAdminClazzAndPlan(Integer clazzId);
}
