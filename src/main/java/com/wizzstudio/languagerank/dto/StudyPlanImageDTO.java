package com.wizzstudio.languagerank.dto;

/*
Created by Ben Wen on 2019/3/31.
*/

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class StudyPlanImageDTO {

    /**
     * 第一份还是第二份
     */
    @Size(min = 1, max = 2)
    @NotNull
    private Integer imageNumber;

    /**
     * 第几天学习计划/奖励的图片
     */
    @Size(min = 1, max = 8)
    @NotNull
    private Integer studyPlanDay;

    /**
     * 语言名
     */
    @NotNull
    private String languageName;

    /**
     * 图片的二进制信息
     */
    @NotNull
    private MultipartFile multipartFile;


    // 以下只有我的奖励才有
    /**
     * 奖励的文字介绍
     */
    private String content;

    /**
     * 奖励的链接
     */
    private String link;

    /**
     * 奖励链接的提取码
     */
    private String password;
}
