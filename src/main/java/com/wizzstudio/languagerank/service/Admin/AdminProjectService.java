package com.wizzstudio.languagerank.service.Admin;

import com.wizzstudio.languagerank.DTO.admin.AdminTotalDTO;
import com.wizzstudio.languagerank.VO.AdminProjectListVO;

public interface AdminProjectService {

    /**
     * 获得班级学习，名称、人数、讨论数
     */
    AdminProjectListVO getAllProject(Integer page, Integer size);

    /**
     * 获取班级的人数、讨论数、膜拜数（暂时无）
     */
    AdminTotalDTO getProjectTotalNumber(Integer id);
}
