package com.wizzstudio.languagerank.domin;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class LanguageCount {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String languageName;

//     计数，由一到七方便取出数据
    private Integer count;

//    该语言学习人总数
    private Integer number;

//    当天增长人数
    private Integer increaseNumber;

}
