package com.wizzstudio.languagerank.domain.fixedrank;

/*
Created by Ben Wen on 2019/4/24.
*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wizzstudio.languagerank.domain.Comment;
import com.wizzstudio.languagerank.domain.user.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
public class FixedRankComment implements Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String languageName;

    @NotNull
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties(value = "fixedRankCommentList")
    private User user;

    @NotNull
    private String comment;

    @NotNull
    private Date saveTime;
}
