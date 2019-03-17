package com.wizzstudio.languagerank.enums;

/*
Created by Ben Wen on 2019/3/17.
*/

import lombok.AllArgsConstructor;
import lombok.Getter;

// 语言指数变化趋势
@Getter
@AllArgsConstructor
public enum LanguageTendEnum {
    INCREASE(1),
    DECREASE(2),
    NO_CHANGE(0);

    private Integer languageTend;
}
