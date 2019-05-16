package com.wizzstudio.languagerank.VO;

/*
Created by Ben Wen on 2019/3/9.
*/

import lombok.Data;

/**
 * 用户登录接口返回的对象类型
 */
@Data
public class WxLogInVO {
    private String openId;

    private Integer userId;

    private String nickName;

    private String avatarUrl;
}
