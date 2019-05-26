package com.wizzstudio.languagerank.VO;

/*
Created by Ben Wen on 2019/5/16.
*/

import lombok.Data;

/**
 * 查询班级基本信息接口的返回参数类型
 */
@Data
public class ClazzMessageVO {
    private String clazzImage;

    private String clazzName;

    private Integer studentNumber;

    private Integer commentNumber;

    /**
     * 该用户今日在该班级是否打卡
     */
    private Boolean isPunchCard;

    //    /**
//     * 该用户是否已加入该班级
//     */
//    private Boolean isInClazz;

    public ClazzMessageVO(String clazzImage, String clazzName, Integer studentNumber, Integer commentNumber) {
        this.clazzImage = clazzImage;
        this.clazzName = clazzName;
        this.studentNumber = studentNumber;
        this.commentNumber = commentNumber;
    }
}
