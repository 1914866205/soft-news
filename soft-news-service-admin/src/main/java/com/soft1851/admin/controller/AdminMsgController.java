package com.soft1851.admin.controller;

import com.soft1851.admin.service.AdminUserService;
import com.soft1851.api.BaseController;
import com.soft1851.api.controller.admin.AdminMsgControllerApi;
import com.soft1851.bo.AdminLoginBO;
import com.soft1851.bo.NewAdminBO;
import com.soft1851.common.exception.GraceException;
import com.soft1851.common.result.GraceResult;
import com.soft1851.common.result.ResponseStatusEnum;
import com.soft1851.common.utils.PageGridResult;
import com.soft1851.pojo.AdminUser;
import com.sun.corba.se.spi.ior.ObjectId;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.soft1851.api.interceptors.BaseInterceptor.REDIS_ADMIN_TOKEN;

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
        } else {
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
        setCookie(request, response, "aToken", token, COOKIE_MONTH);
        setCookie(request, response, "aId", admin.getId(), COOKIE_MONTH);
        setCookie(request, response, "aName", admin.getAdminName(), COOKIE_MONTH);
        System.out.println("aToken" + token);
        System.out.println("aId" + admin.getId());
        System.out.println("aName" + admin.getAdminName());
    }

    @Override
    public GraceResult addNewAdmin(HttpServletRequest request, HttpServletResponse response, NewAdminBO newAdminBO) {
        // 1.BASE64不为空，则代表人脸入库，否则需要用户输入密码和确认密码
        if (StringUtils.isBlank(newAdminBO.getImg64())) {
            if (StringUtils.isBlank(newAdminBO.getPassword()) ||
                    StringUtils.isBlank(newAdminBO.getConfirmationPassword())
            ) {
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

    /**
     * @param page     当前页码
     * @param pageSize 页数
     * @return
     */
    @Override
    public GraceResult getAdminList(Integer page, Integer pageSize) {
        if (page == null) {
            page = COMMON_START_PAGE;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }
        PageGridResult result = adminUserService.queryAdminList(page, pageSize);
        return GraceResult.ok(result);
    }

    /**
     * @param adminId  管理员id
     * @param request  请求
     * @param response 响应
     * @return
     */
    @Override
    public GraceResult adminLogout(String adminId, HttpServletRequest request, HttpServletResponse response) {
        // 1.从redis中删除admin的会话token
        redis.del(REDIS_ADMIN_TOKEN + ":" + adminId);
        // 2.从cookie中清理admin登录的相关信息
        deleteCookie(request, response, "aToken");
        deleteCookie(request, response, "aId");
        deleteCookie(request, response, "aName");
        return GraceResult.ok();
    }

    /**
     *
     * @param request 请求
     * @param response 响应
     * @param newAdminBO 入参
     * @return
     */
    @Override
    public GraceResult updateAdmin(HttpServletRequest request, HttpServletResponse response, NewAdminBO newAdminBO) {
        adminUserService.updateAdmin(newAdminBO.getUsername(),newAdminBO.getFaceId());
        return GraceResult.ok(newAdminBO);
    }

}
