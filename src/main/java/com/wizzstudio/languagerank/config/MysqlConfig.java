package com.wizzstudio.languagerank.config;

/*
Created by Ben Wen on 2019/3/29.
*/


import org.hibernate.dialect.MySQL55Dialect;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MysqlConfig extends MySQL55Dialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
    }
}