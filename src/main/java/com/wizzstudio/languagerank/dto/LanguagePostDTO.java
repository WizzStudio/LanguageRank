package com.wizzstudio.languagerank.dto;

/*
Created by Ben Wen on 2019/4/4.
*/

import com.wizzstudio.languagerank.domain.LanguagePost;
import lombok.Data;

import java.util.List;

@Data
public class LanguagePostDTO {
    private List<LanguagePost> languagePostList;

    private String languageSymbol;
}
