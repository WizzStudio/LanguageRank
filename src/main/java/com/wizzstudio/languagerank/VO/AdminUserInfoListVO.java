package com.wizzstudio.languagerank.VO;

import com.wizzstudio.languagerank.DTO.admin.AdminUserInfoDTO;
import lombok.Data;

import java.util.List;

@Data
public class AdminUserInfoListVO {

    /**
     * 用户信息列表
     */
    private List<AdminUserInfoDTO> adminUserInfoDTOList;
    /**
     * 总数
     */
    private Integer total;

    /**
     * 评论当前页数
     */
    private Integer pageIndex;

    /**
     * 一页最多显示的评论数
     */
    private Integer pageSize;

}
