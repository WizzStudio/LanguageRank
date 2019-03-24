package com.wizzstudio.languagerank.dto;

/*
Created by Ben Wen on 2019/3/24.
*/

import lombok.Data;

@Data
public class LanguageHomePageDTO {
    private Integer joinedNumber;

    private Double fixedFinalExponent;

    private Double fixedFinalExponentIncreasing;

    private CompanyMaxSalaryDTO companyOne;

    private CompanyMaxSalaryDTO companyTwo;
}
