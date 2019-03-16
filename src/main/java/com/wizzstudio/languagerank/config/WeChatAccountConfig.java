package com.wizzstudio.languagerank.config;

/*
Created by Ben Wen on 2019/3/17.
*/

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

    @Component
    @Data
    @ConfigurationProperties("wechat")
    public class WeChatAccountConfig {

        /**
         * 小程序appId
         */
        private String appid;

        /**
         * 小程序appSecret
         */
        private String secret;

        /**
         * 小程序令牌
         */
        private String token;

        /**
         * 加密密钥
         */
        private String aesKey;

        /**
         * 数据格式
         */
        private String msgDataFormat;

        /**
         * 微信模板ID
         */
        private Map<String, String> templateId;
    }
