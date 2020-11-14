package com.soft1851.api.controller.user;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName UserControllerApi.java
 * @Description UserControllerApi
 */
public interface UserControllerApi {

    /**
     * 获取所有的用户
     *
     * @return
     */
    @GetMapping("/users")
    Object getUsers();
}
