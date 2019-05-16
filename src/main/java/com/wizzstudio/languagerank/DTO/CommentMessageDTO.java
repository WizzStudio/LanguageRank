package com.wizzstudio.languagerank.DTO;

/*
Created by Ben Wen on 2019/4/24.
*/

import lombok.Data;

import java.util.Date;

/**
 * 评论内容的传输类型
 */
@Data
public class CommentMessageDTO {
    /**
     * 评论的楼层数
     */
    private Integer floor;

    private Integer userId;

    private String comment;

    private Date saveTime;

    private String nickName;

    private String avatarUrl;
}
