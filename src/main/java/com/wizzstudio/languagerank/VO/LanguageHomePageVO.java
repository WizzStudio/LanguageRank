package com.wizzstudio.languagerank.VO;

/*
Created by Ben Wen on 2019/3/24.
*/

import com.wizzstudio.languagerank.DTO.CompanyMaxSalaryDTO;
import com.wizzstudio.languagerank.domain.GithubPopularProject;
import com.wizzstudio.languagerank.domain.fixedrank.FixedFinalExponent;
import lombok.Data;

import java.util.List;

/**
 * 获取语言主页接口的返回参数类型
 */
@Data
public class LanguageHomePageVO {
//    private String languageSymbol;

//    private Integer languageDifficultyIndex;

//    private Integer joinedNumber;

//    private Double fixedFinalExponent;

//    private Double fixedFinalExponentIncreasing;

    private List<FixedFinalExponent> exponentOfLastSevenDays;

    private List<CompanyMaxSalaryDTO> company;

    private List<GithubPopularProject> githubPopularProjectList;

//    private String languageDevelopmentHistory;
}
