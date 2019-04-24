package com.wizzstudio.languagerank.dto;

/*
Created by Ben Wen on 2019/3/24.
*/

import com.wizzstudio.languagerank.domain.FixedRank.FixedFinalExponent;
import lombok.Data;

import java.util.List;

@Data
public class LanguageHomePageDTO {
    private String languageSymbol;

    private Integer languageDifficultyIndex;

    private Integer joinedNumber;

    private Double fixedFinalExponent;

    private Double fixedFinalExponentIncreasing;

    private List<FixedFinalExponent> exponentOfLastSevenDays;

    private List<CompanyMaxSalaryDTO> company;

    private String languageDevelopmentHistory;
}
