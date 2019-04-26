package com.wizzstudio.languagerank.dto;

/*
Created by Ben Wen on 2019/4/26.
*/

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClazzDTO {
    private Integer clazzId;

    private String clazzName;

    private Integer monitor;

    private Integer studentNumber;
}
