package com.wizzstudio.languagerank.domain.EmployeeRank;

/*
Created by Ben Wen on 2019/4/24.
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
public class EmployeeRankComment implements Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String languageName;

    @NotNull
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties(value = "employeeRankCommentList")
    private User user;

    @NotNull
    private String comment;

    @NotNull
    private Date saveTime;
}
