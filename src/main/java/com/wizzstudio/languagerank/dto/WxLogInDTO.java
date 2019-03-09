package com.wizzstudio.languagerank.dto;

/*
Created by Ben Wen on 2019/3/9.
*/

import lombok.Data;

// 用户登录接口返回的数据
@Data
public class WxLogInDTO {

    private String openId;

    private String session_key;
}
