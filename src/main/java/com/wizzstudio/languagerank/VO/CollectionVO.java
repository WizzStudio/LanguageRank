package com.wizzstudio.languagerank.VO;

/*
Created by Ben Wen on 2019/5/20.
*/

import lombok.Data;

/**
 * 获取我的收藏接口的返回列表内的对象类型
 */
@Data
public class CollectionVO {
    private Integer clazzId;

    private Integer studyPlanDay;

    private String clazzName;

    private String link;

    private String extractedCode;

    private String briefIntroduction;

    private String content;

    public CollectionVO(Integer clazzId, Integer studyPlanDay, String link, String extractedCode, String briefIntroduction, String content) {
        this.clazzId = clazzId;
        this.studyPlanDay = studyPlanDay;
        this.link = link;
        this.extractedCode = extractedCode;
        this.briefIntroduction = briefIntroduction;
        this.content = content;
    }
}
