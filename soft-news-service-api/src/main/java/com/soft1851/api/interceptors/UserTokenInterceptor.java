package com.soft1851.api.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName UserTokenInterceptor.java
 * @Description 用户登录拦截器
 * @createTime 2020年11月17日 15:02:00
 */
public class UserTokenInterceptor extends BaseInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("headerUserId");
        String userToken = request.getHeader("headerUserToken");
        System.out.println("************请求头的数据***************");
        System.out.println(userId);
        System.out.println(userToken);
        System.out.println("************请求头的数据***************");
        return verifyUserIdToken(userId, userToken, REDIS_USER_TOKEN);
    }
}
