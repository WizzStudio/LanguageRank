package com.wizzstudio.languagerank.VO;

/*
Created by Ben Wen on 2019/5/20.
*/

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用户好友排行接口的返回列表内的对象类型
 */
@Data
@AllArgsConstructor
public class UserRelationshipRankVO {
    private Integer userId;

    private String nickName;

    private String avatarUrl;

    private Integer totalScore;
}
