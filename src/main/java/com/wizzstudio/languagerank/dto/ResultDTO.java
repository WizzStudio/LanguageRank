package com.wizzstudio.languagerank.dto;

/*
Created by Ben Wen on 2019/3/6.
*/

import lombok.Data;

@Data
public class ResultDTO<T> {

    //状态码
    private Integer code;

    //信息
    private String msg;

    //数据
    private T data;

    public ResultDTO() {
    }

    public ResultDTO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


}
