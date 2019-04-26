package com.wizzstudio.languagerank.domain.Clazz;

/*
Created by Ben Wen on 2019/4/25.
*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wizzstudio.languagerank.domain.Comment;
import com.wizzstudio.languagerank.domain.User.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
public class ClazzComment implements Comment {
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
    private Date saveTime;
}
