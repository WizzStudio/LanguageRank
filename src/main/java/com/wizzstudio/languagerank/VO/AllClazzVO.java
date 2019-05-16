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
@AllArgsConstructor
@NoArgsConstructor
public class AllClazzVO {
    private Integer clazzId;

    private String clazzName;

//    private String clazzTag;

//    private Integer monitor;

    private Integer studentNumber;
}
