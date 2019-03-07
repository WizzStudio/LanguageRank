package com.wizzstudio.languagerank.domin;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class languagePost {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String languagename;

//      相关语言岗位
    @NotNull
    private String languagePost;

}
