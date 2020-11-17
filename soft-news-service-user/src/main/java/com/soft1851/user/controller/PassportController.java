package com.soft1851.user.controller;

import com.soft1851.api.BaseController;
import com.soft1851.api.controller.user.PassportControllerApi;
import com.soft1851.bo.RegistLoginBO;
import com.soft1851.common.enums.UserStatus;
import com.soft1851.common.result.GraceResult;
import com.soft1851.common.result.ResponseStatusEnum;
import com.soft1851.common.utils.IpUtil;
import com.soft1851.common.utils.JsonUtil;
import com.soft1851.common.utils.RedisOperator;
import com.soft1851.common.utils.SmsUtil;
import com.soft1851.pojo.AppUser;
import com.soft1851.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName PassportController.java
 * @Description TODO
 * @createTime 2020年11月15日 20:23:00
 */
@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PassportController extends BaseController implements PassportControllerApi {
    private final SmsUtil smsUtil;
    private final UserService userService;


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
        log.info("请求时间" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        log.info("请求时间" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        log.info(redis.get(MOBILE_SMSCODE + ":" + mobile));
        log.info("******************************************");
        //把验证码存入redis，用于后续进行验证码
        return GraceResult.ok();
    }

    @Override
    public GraceResult doSign(@Valid RegistLoginBO registLoginBO, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        // 1.判断BingingResult中是否保存了错误的验证信息，如果有，则需要返回
        if (result.hasErrors()) {
            Map<String, String> map = getErrors(result);
            return GraceResult.errorMap(map);
        }

        String mobile = registLoginBO.getMobile();
        String smsCode = registLoginBO.getSmsCode();

        // 1.校验验证码是否匹配
        String redisSmsCode = redis.get(MOBILE_SMSCODE + ":" + mobile);
        if (StringUtils.isBlank(redisSmsCode) || !redisSmsCode.equalsIgnoreCase(smsCode)) {
            return GraceResult.errorCustom(ResponseStatusEnum.SMS_CODE_ERROR);
        }

        // 2.查询数据库，判断该用户注册
        AppUser user = userService.queryMobileIsExist(mobile);
        if (user != null && user.getActiveStatus().equals(UserStatus.FROZEN.type)) {
            //如果用户不为空，并且状态为已冻结，则直接抛出异常，禁止登录
            return GraceResult.errorCustom(ResponseStatusEnum.USER_FROZEN);
        } else if (user == null) {
            //如果用户没有注册过，则为null,需要注册信息入库
            user = userService.createUser(mobile);
        }

        // 3.保存用户分布式会话的相关操作
        int userActiveStatus = user.getActiveStatus();
        if (userActiveStatus != UserStatus.FROZEN.type) {
            //保存token到redis
            String uToken = UUID.randomUUID().toString();
            redis.set(REDIS_USER_TOKEN + ":" + user.getId(), uToken);
            System.out.println("token:");
            System.out.println(uToken);
            redis.set(REDIS_USER_INFO + ":" + user.getId(), JsonUtil.objectToJson(user));
            //保存用户id和token到cookie中
            setCookie(request, response, "utoken", uToken, COOKIE_MONTH);
            setCookie(request, response, "uid", user.getId(), COOKIE_MONTH);
        }
        // 4. 用户登录或注册成功以后，需要删除redis中的短信验证码
        redis.del(MOBILE_SMSCODE + ":" + mobile);

        // 5. 返回用户状态
        return GraceResult.ok(userActiveStatus);
    }
}
