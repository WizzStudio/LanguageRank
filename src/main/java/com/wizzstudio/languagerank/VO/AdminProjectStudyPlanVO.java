package com.wizzstudio.languagerank.VO;

import com.wizzstudio.languagerank.DTO.admin.AdminProjectStudyPlanDTO;
import lombok.Data;

import java.util.List;

@Data
public class AdminProjectStudyPlanVO {

    private String clazzName;

    private Integer monitor;

    private String clazzImage;
    /**
     * 学习计划列表，天数、内容、链接
     */
    private List<AdminProjectStudyPlanDTO> adminProjectStudyPlanDTOList;

}
