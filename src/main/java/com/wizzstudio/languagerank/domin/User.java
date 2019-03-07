package com.wizzstudio.languagerank.domin;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class User {

//    设置主键、自增
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String openId;

//      个人主页上学习的语言
    private String myLanguage;


//      学习天数
    @NotNull
    private Integer accomplishedDay;


//          个性签名
    @Column(length = 64)
    private String motto;

//          个人介绍
    @Column(length = 64)
    private String intro;

}
