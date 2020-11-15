package com.soft1851.api.interceptors;

import com.soft1851.common.exception.GraceException;
import com.soft1851.common.result.ResponseStatusEnum;
import com.soft1851.common.utils.IpUtil;
import com.soft1851.common.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName PassportInterceptor.java
 * @Description 通行证接口拦截器
 * @createTime 2020年11月15日 21:26:00
 */
public class PassportInterceptor implements HandlerInterceptor {
    @Autowired
    public RedisOperator redis;
    public static final String MOBILE_SMSCODE = "mobile:smscode";

    /**
     * 拦截请求，访问controller之前
     *
     * @param request  请求
     * @param response 响应
     * @param handler  拦截
     * @return
     * @throws Exception 抛出异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获得用户ip
        String userIp = IpUtil.getRequestIp(request);
        //检查redis中是否存在这个ip
        boolean keyIsExist = redis.keyIsExist(MOBILE_SMSCODE + ":" + userIp);
        if (keyIsExist) {
            GraceException.display(ResponseStatusEnum.SMS_NEED_WAIT_ERROR);
            System.out.println("短信发送频率太大，请求被拦截。。。。。。。。。。");
            return false;
        }
        // true 请求通过验证，放行
        return true;
    }

    /**
     *请求访问到controller之后，渲染视图之前
     * @param request 请求
     * @param response 响应
     * @param handler 拦截
     * @param modelAndView 视图
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 请求访问到controller之后，渲染视图之后
     * @param request 请求
     * @param response 响应
     * @param handler 拦截
     * @param ex 异常
     * @throws Exception 抛出异常
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
