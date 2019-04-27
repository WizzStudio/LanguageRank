package com.wizzstudio.languagerank.dto;

/*
Created by Ben Wen on 2019/4/27.
*/

import lombok.Data;

import java.util.Date;

@Data
public class ClazzMemberDTO {
    private String nickName;

    private String avatarUrl;

    private Integer uninterruptedStudyPlanDay;

    private Date joinedTime;
}
