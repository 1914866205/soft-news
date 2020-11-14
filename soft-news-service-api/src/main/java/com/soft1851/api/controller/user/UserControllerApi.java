package com.soft1851.api.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName UserControllerApi.java
 * @Description UserControllerApi
 */
@Api(value = "用户信息相关Controller", tags = "用户信息相关Controller")
@RequestMapping("user")
public interface UserControllerApi {

    /**
     * 获取所有的用户
     *
     * @return
     */
    @ApiOperation(value = "获得所有用户信息",notes = "获得所有用户信息",httpMethod = "POST")
    @GetMapping("/users")
    Object getUsers();
}
