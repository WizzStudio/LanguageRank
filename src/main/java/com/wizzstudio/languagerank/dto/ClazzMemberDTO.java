package com.wizzstudio.languagerank.dto;

/*
Created by Ben Wen on 2019/4/27.
*/

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ClazzMemberDTO {
    private String nickName;

    private String avatarUrl;

    private Integer uninterruptedStudyPlanDay;

    private Date joinedTime;

    public ClazzMemberDTO(String nickName, String avatarUrl) {
        this.nickName = nickName;
        this.avatarUrl = avatarUrl;
    }
}
