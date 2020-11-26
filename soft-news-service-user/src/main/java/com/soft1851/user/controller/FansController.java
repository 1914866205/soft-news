package com.soft1851.user.controller;

import com.soft1851.api.BaseController;
import com.soft1851.api.controller.user.FansControllerApi;
import com.soft1851.common.result.GraceResult;
import com.soft1851.user.service.FanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName FansController.java
 * @Description TODO
 * @createTime 2020年11月26日 15:27:00
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FansController extends BaseController implements FansControllerApi {
    private final FanService fanServices;

    /**
     * @param writerId 作者id
     * @param fanId    粉丝id
     * @return
     */
    @Override
    public GraceResult isMefOLLOWThisWriter(String writerId, String fanId) {
        boolean result = fanServices.isMeFollowThisWriter(writerId, fanId);
        return GraceResult.ok(result);
    }

    /**
     * @param writerId 作者id
     * @param fanId    粉丝id
     * @return
     */
    @Override
    public GraceResult follow(String writerId, String fanId) {
        // 判断不为空
        fanServices.follow(writerId, fanId);
        return GraceResult.ok();
    }

    /**
     *
     * @param writerId 作者id
     * @param fanId 粉丝id
     * @return
     */
    @Override
    public GraceResult unfollow(String writerId, String fanId) {
        fanServices.unfollow(writerId, fanId);
        return GraceResult.ok();
    }
}
