package com.wizzstudio.languagerank.dto;

/*
Created by Ben Wen on 2019/4/24.
*/

import lombok.Data;

import java.util.Date;

@Data
public class CommentDTO {
    // 评论楼层数
    private Integer floor;

    private Integer userId;

    private String comment;

    private Date saveTime;

    private String nickName;

    private String avatarUrl;
}
