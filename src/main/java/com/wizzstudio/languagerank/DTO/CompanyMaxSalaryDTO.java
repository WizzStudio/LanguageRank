package com.wizzstudio.languagerank.DTO;

/*
Created by Ben Wen on 2019/3/24.
*/

import lombok.Data;

/**
 * 语言主页中公司最高薪资的传输类型
 */
@Data
public class CompanyMaxSalaryDTO {
    private String companyName;

    private String companySymbol;

    private Integer companyMaxSalary;

    private String companyMaxSalaryPost;
}
