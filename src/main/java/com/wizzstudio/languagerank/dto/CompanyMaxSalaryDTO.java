package com.wizzstudio.languagerank.dto;

/*
Created by Ben Wen on 2019/3/24.
*/

import lombok.Data;

@Data
public class CompanyMaxSalaryDTO {
    private String companyName;

    private Integer companyMaxSalary;

    private String companyMaxSalaryPost;
}
