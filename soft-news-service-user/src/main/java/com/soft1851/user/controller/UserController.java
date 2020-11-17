package com.soft1851.user.controller;

import com.soft1851.api.controller.user.UserControllerApi;
import com.soft1851.common.result.GraceResult;
import com.soft1851.common.result.ResponseStatusEnum;
import com.soft1851.pojo.AppUser;
import com.soft1851.user.mapper.AppUserMapper;
import com.soft1851.user.service.UserService;
import com.soft1851.vo.UserAccountInfoVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController implements UserControllerApi {
    @Resource
    private AppUserMapper appUserMapper;
    private final UserService userService;
    @Override
    public Object getUsers() {
        return GraceResult.ok(appUserMapper.selectAll());
    }

    @Override
    public GraceResult geyUserInfo(String userId) {
        // 1.判断不能为空
        if (StringUtils.isBlank(userId)) {
            return GraceResult.errorCustom(ResponseStatusEnum.UN_LOGIN);
        }
        // 2.根据userId查询用户，调用内部封装方法（复用，扩展方便）
        AppUser user = userService.getUser(userId);
        // 3.设置VO——需要展示的信息
        UserAccountInfoVO accountInfoVO = new UserAccountInfoVO();
        // 4.属性拷贝
        BeanUtils.copyProperties(user, accountInfoVO);
        return GraceResult.ok(accountInfoVO);
    }

}
