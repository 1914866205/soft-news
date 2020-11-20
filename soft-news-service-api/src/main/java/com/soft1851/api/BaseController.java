package com.soft1851.api;

import com.soft1851.common.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName BaseController.java
 * @Description TODO
 * @createTime 2020年11月15日 20:46:00
 */
public class BaseController {
    //这里要声明为public,要不子类就不能使用
    @Autowired
    public RedisOperator redis;

    public static final String MOBILE_SMSCODE = "mobile:smscode";
    public static final String REDIS_USER_TOKEN = "redis_user_token";
    public static final String REDIS_USER_INFO = "redis_user_info";
    public static final String REDIS_ADMIN_INFO = "redis_admin_token";
    public static final Integer COOKIE_MONTH = 30 * 24 * 60 * 60;
    public static final Integer COOKIE_DELETE = 0;
    public static final Integer COMMON_START_PAGE = 1;
    public static final Integer COMMON_PAGE_SIZE = 10;

    @Value("${website.domain-name}")
    public String DOMAIN_NAME;


    /**
     * 获取BO中的错误信息，可以通过统一的异常处理返回给客户端
     *
     * @param result 入参
     * @return
     */
    public Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>(16);
        List<FieldError> errorList = result.getFieldErrors();
        for (FieldError error : errorList) {
            //发送验证错误的时候所对应的某个属性
            String field = error.getField();
            String msg = error.getDefaultMessage();
            map.put(field, msg);
        }
        return map;
    }

    /**
     * 设置cookie
     *
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param maxAge
     */
    public void setCookie(HttpServletRequest request,
                          HttpServletResponse response,
                          String cookieName,
                          String cookieValue,
                          Integer maxAge) {
        try {
            //接收传来的cookieValue
            cookieValue = URLEncoder.encode(cookieValue, "utf-8");
            setCookieValue(request, response, cookieName, cookieValue, maxAge);
            //根据cookieValue，生成新的cookie
            Cookie cookie = new Cookie(cookieName, cookieValue);
            cookie.setMaxAge(maxAge);
            cookie.setDomain(DOMAIN_NAME);
            cookie.setPath("/");
            System.out.println("**********COOKIE**********");
            System.out.println(cookie.toString());
            System.out.println("**********COOKIE**********");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public void setCookieValue(HttpServletRequest request,
                               HttpServletResponse response,
                               String cookieName,
                               String cookieValue,
                               Integer maxAge) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(maxAge);
        cookie.setDomain(DOMAIN_NAME);
        cookie.setPath("/");
        System.out.println("即将响应的Cookie");
        System.out.println(cookie.getValue());
        System.out.println(cookie.getName());
        System.out.println(cookie.getPath());
        System.out.println(cookie.getMaxAge());
        System.out.println("即将响应的Cookie");
        response.addCookie(cookie);
    }
}
