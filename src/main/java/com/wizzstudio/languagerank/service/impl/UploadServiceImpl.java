package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/3/31.
*/

import com.wizzstudio.languagerank.config.QiniuConfig;
import com.wizzstudio.languagerank.service.UploadService;
import com.wizzstudio.languagerank.util.qiniuutil.UploadFactory;
import com.wizzstudio.languagerank.util.qiniuutil.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private QiniuConfig qiniuConfig;

    @Override
    public String uploadImage(MultipartFile image) throws IOException {
        UploadUtil uploadUtil = UploadFactory.createUpload(qiniuConfig.getAccesskey(), qiniuConfig.getSecretkey(),
                qiniuConfig.getBuckethostname(), qiniuConfig.getBucketname());
        return uploadUtil.uploadFile("/filePath/", image);
    }

    @Override
    public String uploadImage(String prefix, MultipartFile image) throws IOException {
        UploadUtil uploadUtil = UploadFactory.createUpload(qiniuConfig.getAccesskey(), qiniuConfig.getSecretkey(),
                qiniuConfig.getBuckethostname(), qiniuConfig.getBucketname());
        return uploadUtil.uploadFile(prefix, image);
    }
}
