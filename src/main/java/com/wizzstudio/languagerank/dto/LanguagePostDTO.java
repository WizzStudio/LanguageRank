package com.wizzstudio.languagerank.dto;

import com.wizzstudio.languagerank.domain.Company;
import com.wizzstudio.languagerank.domain.LanguagePost;
import lombok.Data;

@Data
public class LanguagePostDTO {

//    语言相关岗位
    public LanguagePost languagePost;

//    公司薪资、需求量
    public Company company;


}
