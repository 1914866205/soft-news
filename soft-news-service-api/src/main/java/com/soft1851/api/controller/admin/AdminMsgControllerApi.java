package com.soft1851.api.controller.admin;

import com.soft1851.bo.AdminLoginBO;
import com.soft1851.common.result.GraceResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName AdminMsgControllerApi.java
 * @Description TODO
 * @createTime 2020年11月20日 16:52:00
 */
@Api(value = "管理员维护", tags = {"管理员维护controller"})
@RequestMapping("adminMsg")
public interface AdminMsgControllerApi {
    /**
     * 管理员登录
     *
     * @param adminLoginBO 管理员登录BO类
     * @param request      请求
     * @param response     响应
     * @return
     */
    @PostMapping("adminLogin")
    @ApiOperation(value = "管理员登录", notes = "管理员登录", httpMethod = "POST")
    GraceResult adminLogin(@RequestBody AdminLoginBO adminLoginBO,
                           HttpServletRequest request,
                           HttpServletResponse response);
}