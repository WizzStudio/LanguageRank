package com.wizzstudio.languagerank.DTO.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginDTO {

    private String adminName;

    private String password;

}
