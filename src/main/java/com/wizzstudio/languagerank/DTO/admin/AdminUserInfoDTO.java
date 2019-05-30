package com.wizzstudio.languagerank.DTO.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 后台用户数据，用户信息
 */
@Data
@AllArgsConstructor
public class AdminUserInfoDTO {
    private Integer id;

    private String name;
//  总积分
    private Integer totalScore;
//  打卡总天数
    private Integer totalPunchCardDay;
//  打卡积分
    private Integer totalPunchCardScore;
//膜拜积分
    private Integer totalWorshipScore;

}
