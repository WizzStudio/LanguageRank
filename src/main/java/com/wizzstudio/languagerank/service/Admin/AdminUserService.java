package com.wizzstudio.languagerank.service.Admin;

import com.wizzstudio.languagerank.DTO.admin.AdminTotalDTO;
import com.wizzstudio.languagerank.DTO.admin.AdminUserInfoDTO;
import com.wizzstudio.languagerank.VO.AdminUserInfoListVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

//后台用户数据栏
public interface AdminUserService {

    /**
     * 获取所有（暂时所有用户）用户的部分信息,分页显示
     * @return AdminUserInfoDTO
     */
    AdminUserInfoListVO getUserInfoPage(Integer page, Integer size);

    /**
     * 用户数据页面总人数等，
     */
    AdminTotalDTO getTotalNumber();

    /**
     * 使用用户名查询用户部分信息，暂不支持迷糊匹配
     */
    AdminUserInfoDTO getOneUser(String name);
}
