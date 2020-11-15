package com.soft1851.user.controller;

import com.soft1851.api.controller.user.UserControllerApi;
import com.soft1851.common.result.GraceResult;
import com.soft1851.user.mapper.AppUserMapper;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName UserController.java
 * @Description TODO
 * @createTime 2020年11月14日 18:03:00
 */
@RestController
public class UserController implements UserControllerApi {
    @Resource
    private AppUserMapper appUserMapper;
    @Override
    public Object getUsers() {
        return GraceResult.ok(appUserMapper.selectAll());
    }
}
