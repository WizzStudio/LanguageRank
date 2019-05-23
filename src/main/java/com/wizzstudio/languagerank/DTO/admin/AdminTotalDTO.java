package com.wizzstudio.languagerank.DTO.admin;

import lombok.Data;

/**
 * 后台用户数据/项目管理的字段，两种：全语言的和某个语言的
 */
@Data
public class AdminTotalDTO {
//  总人数
    private Integer userNumber;
//  总讨论数
    private Integer commentNumber;
//  总膜拜数
    private Integer workShipNumber;

}
