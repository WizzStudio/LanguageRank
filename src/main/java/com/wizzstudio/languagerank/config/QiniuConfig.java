package com.wizzstudio.languagerank.config;

/*
Created by Ben Wen on 2019/3/31.
*/

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("qiniu")
public class QiniuConfig {
    /**
     * 七牛云公钥
     */
    private String accesskey;

    /**
     * 七牛云私钥
     */
    private String secretkey;

    /**
     * 七牛云存储空间名
     */
    private String bucketname;

    /**
     * 七牛云我的外链默认域名
     */
    private String buckethostname;
}
