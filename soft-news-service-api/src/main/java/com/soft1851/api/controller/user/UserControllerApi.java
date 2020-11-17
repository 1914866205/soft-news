package com.soft1851.api.controller.user;

import com.soft1851.common.result.GraceResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @ApiOperation(value = "获得所有用户信息",notes = "获得所有用户信息",httpMethod = "Get")
    @GetMapping("/users")
    Object getUsers();

    /**
     * 获得用户基本信息
     * @param userId 用户id
     * @return
     */
    @ApiOperation(value = "获得用户账户信息", notes = "获得用户账户信息", httpMethod = "GET")
    @PostMapping("/userInfo")
    GraceResult geyUserInfo(@RequestParam String userId);

}
