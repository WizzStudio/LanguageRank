package com.wizzstudio.languagerank.domain.user;

/*
Created by Ben Wen on 2019/4/18.
*/

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
public class UserRelationship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer userOne;

    @NotNull
    private Integer userTwo;

    /**
     * 两人建立关系的时间
     */
    @NotNull
    private Date joinedTime;
}
