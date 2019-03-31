package com.wizzstudio.languagerank.util.qiniuutil;

/*
Created by Ben Wen on 2019/3/31.
*/

import com.qiniu.util.Auth;

public class UploadFactory {

    public static UploadUtil createUpload(String accessKey, String secretKeySpec, String bucketHostName, String bucketName) {
        Auth auth = Auth.create(accessKey, secretKeySpec);
        return new QiniuUtil(bucketHostName, bucketName, auth);
    }

}