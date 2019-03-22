package com.wizzstudio.languagerank.service;

/*
Created by Ben Wen on 2019/3/22.
*/

import com.wizzstudio.languagerank.domain.*;

import java.util.List;

public interface EmployeeService {
    /**
     * 查询与该语言相关的热门岗位排行
     */
    List<LanguagePost> getLanguagePost(String languageName);

    /**
     * 查询使用该语言的公司薪资排行
     */
    List<CompanySalary> getCompanySalary(String languageName);

    /**
     * 查询使用该语言的公司岗位需求量排行
     */
    List<CompanyPost> getCompanyPost(String languageName);

    /**
     * 查询几大城市对该语言的需求量
     */
    List<LanguageCity> getLanguageCity(String languageName);
}
