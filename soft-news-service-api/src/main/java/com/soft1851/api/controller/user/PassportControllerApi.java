package com.soft1851.api.controller.user;

import com.soft1851.bo.RegistLoginBO;
import com.soft1851.common.result.GraceResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName PassportController.java
 * @Description 用户的注册登录
 * @createTime 2020年11月15日 20:18:00
 */
@Api(value = "用户注册登录", tags = {"用户注册登录的controller"})
@RequestMapping("passport")
public interface PassportControllerApi {
    /**
     * 发送短信
     *
     * @param mobile  手机号
     * @param request 请求
     * @return 统一响应
     */
    @ApiOperation(value = "获得短信验证码", notes = "获得短信验证码", httpMethod = "GET")
    @GetMapping("/smsCode")
    GraceResult getCode(@RequestParam String mobile, HttpServletRequest request);


    @ApiOperation(value = "一键注册登录接口", notes = "一键注册登录接口", httpMethod = "POST")
    @PostMapping("/sign")
    GraceResult doSign(@RequestBody @Valid RegistLoginBO registLoginBO,
                       BindingResult result,
                       HttpServletRequest request,
                       HttpServletResponse response);

    @PostMapping("/logout")
    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    GraceResult logout(HttpServletRequest request, HttpServletResponse response, @RequestParam String userId);
}
