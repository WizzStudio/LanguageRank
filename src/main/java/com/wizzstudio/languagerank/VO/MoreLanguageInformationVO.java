package com.wizzstudio.languagerank.VO;

/*
Created by Ben Wen on 2019/4/4.
*/

import com.wizzstudio.languagerank.domain.Company;
import com.wizzstudio.languagerank.domain.Language;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 获取更多语言信息接口的返回参数类型
 */
@Data
public class MoreLanguageInformationVO {
    private Language language;

    private Map<String, String> languageApplicationFields;

    private Map<String, String> languageUse;

    private List<Company> companyList;

    /**
     * 语言优点
     */
    @NotNull
    private List<String> languageAdvantage;

    /**
     * 语言缺点
     */
    @NotNull
    private List<String> languageDisadvantage;
}
