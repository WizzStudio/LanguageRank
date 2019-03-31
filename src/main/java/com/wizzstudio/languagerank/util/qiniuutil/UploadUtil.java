package com.wizzstudio.languagerank.util.qiniuutil;

/*
Created by Ben Wen on 2019/3/31.
*/

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface UploadUtil {
    String uploadFile(MultipartFile multipartFile) throws IOException;

    String uploadFile(String filePath, MultipartFile multipartFile) throws IOException;

    String uploadFile(MultipartFile multipartFile, String fileName) throws IOException;

    String uploadFile(MultipartFile multipartFile, String fileName, String filePath) throws IOException;

    String uploadFile(File file) throws IOException;

    String uploadFile(String filePath, File file) throws IOException;

    String uploadFile(File file, String fileName) throws IOException;

    String uploadFile(File file, String fileName, String filePath) throws IOException;

    String uploadFile(byte[] data) throws IOException;

    String uploadFile(String filePath, byte[] data) throws IOException;

    String uploadFile(byte[] data, String fileName) throws IOException;

    String uploadFile(byte[] data, String fileName, String filePath) throws IOException;
}