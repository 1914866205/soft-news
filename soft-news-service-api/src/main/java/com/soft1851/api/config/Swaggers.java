package com.soft1851.api.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName Swaggers.java
 * @Description TODO
 * @createTime 2020年11月14日 18:44:00
 */
@Configuration
@EnableSwagger2
public class Swaggers {

    @Bean
    public Docket createRestApi() {

        Predicate<RequestHandler> adminPredicate = RequestHandlerSelectors.basePackage("com.soft1851.admin.controller");
        Predicate<RequestHandler> userPredicate = RequestHandlerSelectors.basePackage("com.soft1851.user.controller");
        Predicate<RequestHandler> filesPredicate = RequestHandlerSelectors.basePackage("com.soft1851.files.controller");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(Predicates.or(userPredicate, adminPredicate, filesPredicate))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("新闻丶自媒体接口api")
                .contact(new Contact("taotao", "http://www.qq.com","1914866205@qq.com"))
                .description("1.0.1")
                .termsOfServiceUrl("https://www.taotao.com")
                .build();
    }
}
