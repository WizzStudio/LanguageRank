package com.wizzstudio.languagerank.domain.clazz;

/*
Created by Ben Wen on 2019/5/17.
*/

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
public class Worship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 膜拜者userId
     */
    @NotNull
    private Integer worshippingUser;

    /**
     * 被膜拜者userId
     */
    @NotNull
    private Integer worshippedUser;

    /**
     * 膜拜时间
     */
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date worshipTime;
}
