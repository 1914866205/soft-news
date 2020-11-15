package com.soft1851.api.controller.user;

import com.soft1851.common.result.GraceResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

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
}
