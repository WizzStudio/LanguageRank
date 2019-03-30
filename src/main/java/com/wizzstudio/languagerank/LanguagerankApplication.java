package com.wizzstudio.languagerank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 开启定时任务
//@EnableJpaAuditing // 实体类时间字段设置
public class LanguagerankApplication {

    public static void main(String[] args) {
        SpringApplication.run(LanguagerankApplication.class, args);
    }

}
