package com.wizzstudio.languagerank.dto;


import lombok.Data;

@Data
public class FixedRankDTO {

//    TOBLE排行榜
    public String rankstr;

//    语言热度指数
    private Integer languageExponent;

//    语言热度变化趋势(0不变,1上升,2下降)
    private Integer languageTend;

}
