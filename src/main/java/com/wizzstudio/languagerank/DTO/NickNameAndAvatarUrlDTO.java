package com.wizzstudio.languagerank.DTO;

/*
Created by Ben Wen on 2019/5/24.
*/

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NickNameAndAvatarUrlDTO {
    private String nickName;

    private String avatarUrl;
}
