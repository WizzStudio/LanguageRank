package com.wizzstudio.languagerank.VO;

/*
Created by Ben Wen on 2019/4/4.
*/

import com.wizzstudio.languagerank.domain.employeerank.LanguagePost;
import lombok.Data;

import java.util.List;

/**
 * 获取语言热门岗位接口的返回参数类型
 */
@Data
public class LanguagePostVO {
    private List<LanguagePost> languagePostList;

    private String languageSymbol;
}
