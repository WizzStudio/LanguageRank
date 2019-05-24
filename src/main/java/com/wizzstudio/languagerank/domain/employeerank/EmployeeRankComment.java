package com.wizzstudio.languagerank.domain.employeerank;

/*
Created by Ben Wen on 2019/4/24.
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
public class EmployeeRankComment {
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date saveTime;
}
