package com.wizzstudio.languagerank.domain.Clazz;

/*
Created by Ben Wen on 2019/4/25.
*/

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
    private Integer monitor;

    @NotNull
    private Integer studentNumber;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "UserClazz",
            joinColumns = {@JoinColumn(name = "clazzId")},
            inverseJoinColumns = {@JoinColumn(name = "userId")}
    )
    private List<User> userList;
}
