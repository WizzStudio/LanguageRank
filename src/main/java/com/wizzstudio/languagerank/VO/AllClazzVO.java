package com.wizzstudio.languagerank.VO;

/*
Created by Ben Wen on 2019/4/26.
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取全部班级列表接口返回列表内的对象类型
 */
@Data
@NoArgsConstructor
public class AllClazzVO {
    private Integer clazzId;

    private String clazzName;

//    private String clazzTag;

//    private Integer monitor;

    private Integer studentNumber;
//
//    /**
//     * 用户是否加入了该班级
//     */
//    private Boolean isInClazz;

    public AllClazzVO(Integer clazzId, String clazzName, Integer studentNumber) {
        this.clazzId = clazzId;
        this.clazzName = clazzName;
        this.studentNumber = studentNumber;
    }
}
