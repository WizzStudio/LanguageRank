package com.wizzstudio.languagerank.domain.Clazz;

/*
Created by Ben Wen on 2019/4/25.
*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wizzstudio.languagerank.domain.User.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
public class Clazz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clazzId;

    @NotNull
    private String clazzName;

    @NotNull
    // 班长userId，当班长未定时默认为0
    private Integer monitor;

    // 该班级学生人数(写切面自增还是写redis？？？)
    @NotNull
    private Integer studentNumber;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = "clazzList")
    @JoinTable(
            name = "UserClazz",
            joinColumns = {@JoinColumn(name = "clazzId")},
            inverseJoinColumns = {@JoinColumn(name = "userId")}
    )
    private List<User> userList;
}
