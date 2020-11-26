package com.soft1851.article;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName ArticleApplication.java
 * @Description TODO
 * @createTime 2020年11月24日 15:02:00
 */
@SpringBootApplication
@MapperScan(basePackages = "com.soft1851.article.mapper")
@ComponentScan(basePackages = {"com.soft1851", "org.n3r.idworker"})
public class ArticleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArticleApplication.class, args);
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
