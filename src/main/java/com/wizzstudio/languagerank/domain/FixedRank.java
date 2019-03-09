package com.wizzstudio.languagerank.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class FixedRank {

    @Id
    @GeneratedValue
    private Integer id;

    // TOBLE榜单数据
    private String rankStr;

    private Date createTime;

    private Date updateTime;

}
