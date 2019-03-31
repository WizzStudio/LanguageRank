package com.wizzstudio.languagerank.util.qiniuutil;

/*
Created by Ben Wen on 2019/3/31.
*/

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
public class QiniuUtil implements UploadUtil {

    private String bucketHostName;

    private String bucketName;

    private Auth auth;

    private UploadManager uploadManager = new UploadManager();

    /**
     * 构造函数
     *
     * @param bucketHostName 七牛域名
     * @param bucketName     七牛空间名
     * @param auth           七牛授权
     */
    public QiniuUtil(String bucketHostName, String bucketName, Auth auth) {
        this.bucketHostName = bucketHostName;
        this.bucketName = bucketName;
        this.auth = auth;
    }

    public String generate(){
        return this.generateToken();
    }


    /**
     * 根据spring mvc 文件接口上传
     *
     * @param multipartFile spring mvc 文件接口
     * @return 文件路径
     * @throws IOException
     */
    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        byte[] bytes = getBytesWithMultipartFile(multipartFile);
        return this.uploadFile(bytes);
    }

    /**
     * 根据spring mvc 文件接口上传
     *
     * @param filePath      文件前缀,例如:/test或者/test/
     * @param multipartFile spring mvc 文件接口
     * @return 文件路径
     * @throws IOException
     */
    @Override
    public String uploadFile(String filePath, MultipartFile multipartFile) throws IOException {
        byte[] bytes = getBytesWithMultipartFile(multipartFile);
        return this.uploadFile(filePath, bytes);
    }

    /**
     * 根据spring mvc 文件接口上传
     *
     * @param multipartFile spring mvc 文件接口
     * @param fileName      文件名
     * @return 文件路径
     * @throws IOException
     */
    @Override
    public String uploadFile(MultipartFile multipartFile, String fileName) throws IOException {
        byte[] bytes = getBytesWithMultipartFile(multipartFile);
        return this.uploadFile(bytes, fileName);
    }


    /**
     * 根据spring mvc 文件接口上传
     *
     * @param multipartFile spring mvc 文件接口
     * @param fileName      文件名
     * @param filePath      文件前缀,例如:/test或者/test/
     * @return 文件路径
     * @throws IOException
     */
    @Override
    public String uploadFile(MultipartFile multipartFile, String fileName, String filePath) throws IOException {
        byte[] bytes = getBytesWithMultipartFile(multipartFile);
        return this.uploadFile(bytes, fileName, filePath);
    }


    /**
     * 根据spring mvc 文件接口上传
     *
     * @param file 文件
     * @return 文件路径
     * @throws IOException
     */
    @Override
    public String uploadFile(File file) throws IOException {
        return this.uploadFile(file, null, null);
    }

    /**
     * 根据spring mvc 文件接口上传
     *
     * @param file     文件
     * @param filePath 文件前缀,例如:/test或者/test/
     * @return 文件路径
     * @throws IOException
     */
    @Override
    public String uploadFile(String filePath, File file) throws IOException {
        return this.uploadFile(file, null, filePath);
    }

    /**
     * 根据spring mvc 文件接口上传
     *
     * @param file     文件
     * @param fileName 文件名
     * @return 文件路径
     * @throws IOException
     */
    @Override
    public String uploadFile(File file, String fileName) throws IOException {
        return this.uploadFile(file, fileName, null);
    }

    /**
     * 根据spring mvc 文件接口上传
     *
     * @param file     文件
     * @param fileName 文件名
     * @param filePath 文件前缀,例如:/test或者/test/
     * @return 文件路径
     * @throws IOException
     */
    @Override
    public String uploadFile(File file, String fileName, String filePath) throws IOException {
        String key = preHandle(fileName, filePath);
        Response response = null;
        try {
            response = this.uploadManager.put(file, key, this.generateToken());
        } catch (QiniuException e) {
            log.warn("QiniuException:", e);
            throw new IOException(e.getMessage());
        }
        return this.getUrlPath(response);
    }

    /**
     * 根据spring mvc 文件接口上传
     *
     * @param data 文件
     * @return 文件路径
     * @throws IOException
     */
    @Override
    public String uploadFile(byte[] data) throws IOException {
        return this.uploadFile(data, null, null);
    }

    /**
     * 根据spring mvc 文件接口上传
     *
     * @param data     文件
     * @param filePath 文件前缀,例如:/test或者/test/
     * @return 文件路径
     * @throws IOException
     */
    @Override
    public String uploadFile(String filePath, byte[] data) throws IOException {
        return this.uploadFile(data, null, filePath);
    }

    /**
     * 根据spring mvc 文件接口上传
     *
     * @param data     文件
     * @param fileName 文件名
     * @return 文件路径
     * @throws IOException
     */
    @Override
    public String uploadFile(byte[] data, String fileName) throws IOException {
        return this.uploadFile(data, fileName, null);
    }

    /**
     * 根据spring mvc 文件接口上传
     *
     * @param data     文件
     * @param fileName 文件名
     * @param filePath 文件前缀,例如:/test或者/test/
     * @return 文件路径
     * @throws IOException
     */
    @Override
    public String uploadFile(byte[] data, String fileName, String filePath) throws IOException {
        String key = preHandle(fileName, filePath);
        Response response;
        try {
            response = this.uploadManager.put(data, key, generateToken());
        } catch (QiniuException e) {
            log.error("QiniuException:", e);
            throw new IOException(e.getMessage());
        }
        return this.getUrlPath(response);
    }

    private byte[] getBytesWithMultipartFile(MultipartFile multipartFile) {
        try {
            return multipartFile.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String preHandle(String fileName, String filePath) throws IOException {
        if (StringUtils.isNotBlank(fileName) && !fileName.contains(".")) {
            throw new IOException("文件名必须包含尾缀");
        }
        if (StringUtils.isNotBlank(filePath) && !filePath.startsWith("/")) {
            throw new IOException("前缀必须以'/'开头");
        }
        String name = StringUtils.isBlank(fileName) ? RandomStringUtils.randomAlphanumeric(32) : fileName;
        if (StringUtils.isBlank(filePath)) {
            return name;
        }
        String prefix = filePath.replaceFirst("/", "");
        return (prefix.endsWith("/") ? prefix : prefix.concat("/")).concat(name);
    }

    private String generateToken() {
        return this.auth.uploadToken(bucketName);
    }


    private String getUrlPath(Response response) throws IOException {
        if (!response.isOK()) {
            throw new IOException("文件上传失败");
        }
        DefaultPutRet defaultPutRet;
        try {
            defaultPutRet = response.jsonToObject(DefaultPutRet.class);
        } catch (QiniuException e) {
            log.warn("QiniuException", e);
            throw new IOException(e.getMessage());
        }
        String key = defaultPutRet.key;
        if (key.startsWith(bucketHostName)) {
            return key;
        }
        return bucketHostName + (key.startsWith("/") ? key : "/" + key);
    }

}
