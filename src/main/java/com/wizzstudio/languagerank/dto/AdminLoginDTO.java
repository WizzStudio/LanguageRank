package com.wizzstudio.languagerank.dto;

import lombok.Data;

@Data
public class AdminLoginDTO {

    private String adminName;
    private String password;

    public AdminLoginDTO() {}

    public AdminLoginDTO(String adminName, String password) {
        this.adminName = adminName;
        this.password = password;
    }
}
