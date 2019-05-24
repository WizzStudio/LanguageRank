package com.wizzstudio.languagerank.VO;

import com.wizzstudio.languagerank.DTO.admin.AdminProjectDTO;
import lombok.Data;

import java.util.List;

@Data
public class AdminProjectListVO {

    /**
     * 项目列表
     */
    private List<AdminProjectDTO> adminProjectDTOList;
    /**
     * 总数
     */
    private Integer total;

    /**
     * 评论当前页数
     */
    private Integer pageIndex;

    /**
     * 一页最多显示
     */
    private Integer pageSize;

}
