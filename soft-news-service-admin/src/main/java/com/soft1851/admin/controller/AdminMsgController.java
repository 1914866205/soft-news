package com.soft1851.admin.controller;

import com.soft1851.admin.service.AdminUserService;
import com.soft1851.api.BaseController;
import com.soft1851.api.controller.admin.AdminMsgControllerApi;
import com.soft1851.bo.AdminLoginBO;
import com.soft1851.bo.NewAdminBO;
import com.soft1851.common.exception.GraceException;
import com.soft1851.common.result.GraceResult;
import com.soft1851.common.result.ResponseStatusEnum;
import com.soft1851.pojo.AdminUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName AdminMsgController.java
 * @Description TODO
 * @createTime 2020年11月20日 17:03:00
 */
@RestController
public class AdminMsgController extends BaseController implements AdminMsgControllerApi {

    @Autowired
    private AdminUserService adminUserService;

    @Override
    public GraceResult adminLogin(AdminLoginBO adminLoginBO, HttpServletRequest request, HttpServletResponse response) {
        //查询用户是否存在
        AdminUser admin = adminUserService.queryAdminByUsername(adminLoginBO.getUsername());
        if (admin == null) {
            return GraceResult.errorCustom(ResponseStatusEnum.ADMIN_NOT_EXIT_ERROR);
        }
        //判断密码是否匹配
        boolean isPwdMatch = BCrypt.checkpw(adminLoginBO.getPassword(), admin.getPassword());
        if (isPwdMatch) {
            doLoginSetting(admin, request, response);
            return GraceResult.ok();
        }else {
            //密码不匹配
            return GraceResult.errorCustom(ResponseStatusEnum.ADMIN_NOT_EXIT_ERROR);
        }
    }

    @Override
    public GraceResult adminInExist(String username) {
        checkAdminExist(username);
        return GraceResult.ok();
    }

    private void checkAdminExist(String username) {
        AdminUser admin = adminUserService.queryAdminByUsername(username);
        if (admin != null) {
            GraceException.display(ResponseStatusEnum.ADMIN_USERNAME_EXIST_ERROR);
        }
    }

    private void doLoginSetting(AdminUser admin, HttpServletRequest request, HttpServletResponse response) {
        //保存token放入redis
        String token = UUID.randomUUID().toString();
        redis.set(REDIS_ADMIN_INFO + ":" + admin.getId(), token);
        //保存admin登录基本token到cookie中
        setCookie(request, response, "aToken",token , COOKIE_MONTH);
        setCookie(request, response, "aId",admin.getId() , COOKIE_MONTH);
        setCookie(request, response, "aName", admin.getAdminName(), COOKIE_MONTH);
        System.out.println("aToken"+token );
        System.out.println("aId"+admin.getId()  );
        System.out.println("aName"+ admin.getAdminName());
    }

    @Override
    public GraceResult addNewAdmin(HttpServletRequest request, HttpServletResponse response, NewAdminBO newAdminBO) {
        // 1.BASE64不为空，则代表人脸入库，否则需要用户输入密码和确认密码
        if (StringUtils.isBlank(newAdminBO.getImg64())) {
            if (StringUtils.isBlank(newAdminBO.getPassword())||
            StringUtils.isBlank(newAdminBO.getConfirmationPassword())
            ){
                return GraceResult.errorCustom(ResponseStatusEnum.ADMIN_PASSWORD_NULL_ERROR);
            }
        }
        // 2.密码不为空,则必须判断两次输入一致
        if (StringUtils.isNotBlank(newAdminBO.getPassword())) {
            if (!newAdminBO.getUsername().equalsIgnoreCase(newAdminBO.getConfirmationPassword())) {
                return GraceResult.errorCustom(ResponseStatusEnum.ADMIN_PASSWORD_ERROR);
            }
        }

        // 3.校验用户名唯一
        checkAdminExist(newAdminBO.getUsername());

        // 4.调用service存入admin信息
        adminUserService.createAdminUser(newAdminBO);
        return GraceResult.ok();
    }
}
