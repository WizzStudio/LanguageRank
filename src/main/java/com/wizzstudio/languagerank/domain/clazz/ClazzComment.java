package com.wizzstudio.languagerank.domain.clazz;

/*
Created by Ben Wen on 2019/4/25.
*/

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wizzstudio.languagerank.domain.user.User;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
public class ClazzComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer clazzId;

    // 需要查这个用户的昵称头像，但不需要知道该用户有哪些评论，用多对一单向
    @NotNull
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties(value = "clazzCommentList")
    private User user;

    @NotNull
    private String comment;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date saveTime;
}
