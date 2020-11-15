package com.soft1851.user.controller;

import com.soft1851.api.controller.user.PassportControllerApi;
import com.soft1851.common.result.GraceResult;
import com.soft1851.common.utils.SmsUtil;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName PassportController.java
 * @Description TODO
 * @createTime 2020年11月15日 20:23:00
 */
@RestController
public class PassportController implements PassportControllerApi {
    @Resource
    private SmsUtil smsUtil;

    @Override
    public GraceResult getCode(String mobile, HttpServletRequest request) {
        //生成随机验证码并发送短信
        String random = (int) ((Math.random() * 9 + 1) * 100000) + "";
        smsUtil.sendSms(mobile, random);
        return GraceResult.ok();
    }
}
