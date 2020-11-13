package com.soft1851.api.controller.user;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HelloControllerApi.java
 * @Description HelloControllerApi接口
 * @createTime 2020年11月13日 21:40:00
 */
public interface HelloControllerApi {

    @GetMapping("/hello")
    Object hello();
}
