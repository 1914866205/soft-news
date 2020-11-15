package com.soft1851.api;

import com.soft1851.common.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;

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
}
