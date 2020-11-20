package com.soft1851.api.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.soft1851.api.BaseController.REDIS_ADMIN_INFO;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName AdminTokenInterceptor.java
 * @Description TODO
 * @createTime 2020年11月20日 18:01:00
 */
public class AdminTokenInterceptor extends BaseInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String adminUserId = request.getHeader("adminUserId");
        String adminUserToken = request.getHeader("adminUserToken");

        System.out.println("=========================================");
        System.out.println("AdminTokenInterceptor-adminUserId="+adminUserId);
        System.out.println("AdminTokenInterceptor-adminUserToken="+adminUserToken);
        System.out.println("=========================================");
        return verifyUserIdToken(adminUserId, adminUserToken, REDIS_ADMIN_INFO);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
