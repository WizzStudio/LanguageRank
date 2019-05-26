package com.wizzstudio.languagerank.DTO.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminProjectDTO {
    private Integer id;

    private String projectName;
//  项目人数
    private Integer number;
//  项目讨论数
    private Integer comment;

}
