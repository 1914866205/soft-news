package com.soft1851.api.config;

import com.soft1851.api.interceptors.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName InterceptorConfig.java
 * @Description 拦截器
 * @createTime 2020年11月15日 21:35:00
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Bean
    public PassportInterceptor passportInterceptor() {
        return new PassportInterceptor();
    }

    @Bean
    public UserTokenInterceptor userTokenInterceptor() {
        return new UserTokenInterceptor();
    }

    @Bean
    public AdminTokenInterceptor adminTokenInterceptor(){
        return new AdminTokenInterceptor();
    }


    @Bean
    public UserActiveInterceptor userActiveInterceptor() {
        return new UserActiveInterceptor();
    }

    @Bean
    public ArticleReadInterceptor articleReadInterceptor(){
        return new ArticleReadInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器，添加拦截路由
        registry.addInterceptor(passportInterceptor()).addPathPatterns("/passport/smsCode");

        registry.addInterceptor(userTokenInterceptor())
                .addPathPatterns("/user/userBasicInfo")
                .addPathPatterns("/user/updateUserInfo");

//        registry.addInterceptor(userActiveInterceptor())
//                .addPathPatterns("/user/follow");

        registry.addInterceptor(adminTokenInterceptor())
                .addPathPatterns("/adminMsg/adminIsExist");

        registry.addInterceptor(articleReadInterceptor())
                .addPathPatterns("/article/readArticle");
    }



}
