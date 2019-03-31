package com.wizzstudio.languagerank.service;

/*
Created by Ben Wen on 2019/3/31.
*/

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {

    /**
     * 将图片上传至七牛云
     * @param image 将要上传的图片
     * @return 文件路径
     */
    String uploadImage(MultipartFile image)  throws IOException;

    /**
     * 将图片上传至七牛云
     * @param prefix 文件前缀，例：/blog/
     * @param image 将要上传的图片
     * @return 文件路径
     */
    String uploadImage(String prefix, MultipartFile image)  throws IOException;
}
