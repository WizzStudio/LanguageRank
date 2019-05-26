package com.wizzstudio.languagerank.VO;

/*
Created by Ben Wen on 2019/5/21.
*/

import lombok.Data;

@Data
public class LanguageMessageVO {
    private String languageSymbol;

    /**
     * 该语言粉丝数
     */
    private Integer languageFans;
}
