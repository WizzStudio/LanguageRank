package com.wizzstudio.languagerank.domain;

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

    // 计数，由一到七方便取出数据
    private Integer count;

    // 该语言学习人总数，24小时更新一次(减少数据库更新次数)
    private Integer number;

    // 当天增长人数，每访问一次更新一次
    private Integer increaseNumber;

}
