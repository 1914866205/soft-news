package com.soft1851.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName Application.java
 * @Description admin工程启动主类
 * @createTime 2020年11月20日 17:09:30
 */
@SpringBootApplication
@MapperScan(basePackages = "com.soft1851.admin.mapper")
@ComponentScan(basePackages = {"com.soft1851", "org.n3r.idworker"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
