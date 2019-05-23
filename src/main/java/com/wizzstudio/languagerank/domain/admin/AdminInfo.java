package com.wizzstudio.languagerank.domain.admin;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@DynamicInsert
public class AdminInfo {

    // 管理员Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adminId;

    // 管理员账号，唯一
    @Column
    @NotNull
    private String adminName;

    // 管理员密码
    @Column
    @Size(min = 6,max = 12)
    @NotNull
    private String adminPass;

}
