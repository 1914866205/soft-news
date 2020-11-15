package com.soft1851.user.controller;

import com.soft1851.api.BaseController;
import com.soft1851.api.controller.user.PassportControllerApi;
import com.soft1851.common.result.GraceResult;
import com.soft1851.common.utils.IpUtil;
import com.soft1851.common.utils.RedisOperator;
import com.soft1851.common.utils.SmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName PassportController.java
 * @Description TODO
 * @createTime 2020年11月15日 20:23:00
 */
@RestController
@Slf4j
public class PassportController  extends BaseController implements PassportControllerApi {
    @Resource
    private SmsUtil smsUtil;

    @Override
    public GraceResult getCode(String mobile, HttpServletRequest request) {
        //获得用户IP
        String userIp = IpUtil.getRequestIp(request);
        //根基用户的ip进行限制，限制用户在60秒内只能获取一次验证码
        redis.setnx60s(MOBILE_SMSCODE + ":" + userIp, userIp);
        //生成随机验证码并发送短信
        String random = (int) ((Math.random() * 9 + 1) * 100000) + "";
//        smsUtil.sendSms(mobile, random);
        redis.set(MOBILE_SMSCODE + ":" + mobile, random, 30 * 60);
        log.info("******************************************");
        log.info("请求时间"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        log.info("请求时间"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        log.info(redis.get(MOBILE_SMSCODE + ":" + mobile));
        log.info("******************************************");
        //把验证码存入redis，用于后续进行验证码
        return GraceResult.ok();
    }
}
