package com.wizzstudio.languagerank.DTO;

/*
Created by Ben Wen on 2019/3/7.
*/

import lombok.Data;

/**
 * 用户登录接口的接收参数类型
 */
@Data
public class WxInfoDTO {
    private String code;

    private String encryptedData;

    private String iv;
}
