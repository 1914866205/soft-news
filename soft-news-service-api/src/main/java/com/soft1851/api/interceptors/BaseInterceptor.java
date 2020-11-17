package com.soft1851.api.interceptors;

import com.soft1851.common.exception.GraceException;
import com.soft1851.common.result.ResponseStatusEnum;
import com.soft1851.common.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName BaseInterceptor.java
 * @Description 基础拦截器
 * @createTime 2020年11月17日 14:54:00
 */
public class BaseInterceptor {
    @Autowired
    private RedisOperator redis;

    public static final String REDIS_USER_TOKEN = "redis_user_token";
    public static final String REDIS_ADMIN_TOKEN = "redis_admin_token";

    public boolean verifyUserIdToken(String id, String token, String redisKeyPrefix) {
        System.out.println("id" + id);
        System.out.println("token" + token);
        System.out.println("redisKeyPrefix" + redisKeyPrefix);
        System.out.println("1" + StringUtils.isNotBlank(id));
        System.out.println("2" + StringUtils.isNotBlank(token));
        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(token)) {
            String uniqueToken = redis.get(redisKeyPrefix + ":" + id);
            System.out.println("3" + StringUtils.isBlank(uniqueToken));
            if (StringUtils.isBlank(uniqueToken)) {
                GraceException.display(ResponseStatusEnum.UN_LOGIN);
                return false;
            } else {
                System.out.println("4" + !uniqueToken.equals(token));
                if (!uniqueToken.equals(token)) {
                    GraceException.display(ResponseStatusEnum.TICKET_INVALID);
                    return false;
                }
            }
        } else {
            GraceException.display(ResponseStatusEnum.UN_LOGIN);
            return false;
        }
        //false:请求被拦截，被驳回，验证出现问题，
        //true:请求在经过验证校验以后，确认是可以放行的
        return true;
    }
}
