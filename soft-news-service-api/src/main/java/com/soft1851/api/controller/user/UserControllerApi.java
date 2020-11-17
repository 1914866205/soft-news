package com.soft1851.api.controller.user;

import com.soft1851.bo.UpdateUserInfoBO;
import com.soft1851.common.result.GraceResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    @ApiOperation(value = "获得所有用户信息", notes = "获得所有用户信息", httpMethod = "GET")
    @GetMapping("/users")
    Object getUsers();

    /**
     * 获得用户基本信息
     *
     * @param userId 用户id
     * @return
     */
    @ApiOperation(value = "获得用户账户信息", notes = "获得用户账户信息", httpMethod = "GET")
    @PostMapping("/userInfo")
    GraceResult geyUserInfo(@RequestParam String userId);


    @PostMapping("/updateUserInfo")
    @ApiOperation(value = "完善用户信息", notes = "完善用户信息", httpMethod = "POST")
    GraceResult updateUserInfo(@RequestBody @Valid UpdateUserInfoBO updateUserInfoBO, BindingResult result);

}
