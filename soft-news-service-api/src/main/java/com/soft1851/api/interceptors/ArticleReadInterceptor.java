package com.soft1851.api.interceptors;

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
 * @ClassName ArticleReadInterceptor.java
 * @Description TODO
 * @createTime 2020年11月26日 19:02:00
 */
public class ArticleReadInterceptor extends BaseInterceptor implements HandlerInterceptor {

    @Autowired
    public RedisOperator redis;
    public static final String REDIS_ALREADY_READ = "redis_already_read";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String articleId = request.getParameter("articleId");
        String userIp = IpUtil.getRequestIp(request);
        boolean isExist = redis.keyIsExist(REDIS_ALREADY_READ + ":" + articleId + ":" + userIp);
        return !isExist;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
