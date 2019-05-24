package com.wizzstudio.languagerank.DTO;

/*
Created by Ben Wen on 2019/4/27.
*/

import lombok.Data;

/**
 * 用户已加入的班级的传输对象
 */
@Data
public class UserClazzDTO {
    private Integer clazzId;

    private String clazzName;

    private String monitorNickName;

    private String clazzImage;
}
