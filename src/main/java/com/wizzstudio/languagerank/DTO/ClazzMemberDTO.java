package com.wizzstudio.languagerank.DTO;

/*
Created by Ben Wen on 2019/4/27.
*/

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 查询班级所有成员的传输类型
 */
@Data
@NoArgsConstructor
public class ClazzMemberDTO {
    private Integer userId;

    private String nickName;

    private String avatarUrl;

    private Integer uninterruptedStudyPlanDay;

    private Date joinedTime;

    public ClazzMemberDTO(String nickName, String avatarUrl) {
        this.nickName = nickName;
        this.avatarUrl = avatarUrl;
    }
}
