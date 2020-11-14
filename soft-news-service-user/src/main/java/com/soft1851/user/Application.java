package com.soft1851.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName com.soft1851.user.Application.java
 * @Description TODO
 * @createTime 2020年11月13日 21:02:00
 */
@SpringBootApplication
@MapperScan(basePackages = "com.soft1851.user.mapper")
@ComponentScan("com.soft1851")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
