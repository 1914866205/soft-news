package com.soft1851.admin.controller;

import com.soft1851.admin.service.FriendLinkService;
import com.soft1851.api.BaseController;
import com.soft1851.api.controller.admin.FriendLinkControllerApi;
import com.soft1851.bo.SaveFriendLinkBO;
import com.soft1851.common.result.GraceResult;
import com.soft1851.mo.FriendLinkMO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName FriendLinkController.java
 * @Description TODO
 * @createTime 2020年11月24日 13:38:00
 */
@RestController
@RequiredArgsConstructor
public class FriendLinkController extends BaseController implements FriendLinkControllerApi {

    @Resource
    private FriendLinkService friendLinkService;

    @Override
    public GraceResult saveOrUpdateFriendLink(SaveFriendLinkBO saveFriendLinkBO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> map = getErrors(result);
            return GraceResult.errorMap(map);
        }
        FriendLinkMO saveFriendLinkMO = new FriendLinkMO();
        //将BO通过属性拷贝到MO
        BeanUtils.copyProperties(saveFriendLinkBO, saveFriendLinkMO);
        saveFriendLinkMO.setCreateTime(new Date());
        saveFriendLinkMO.setUpdateTime(new Date());
        friendLinkService.saveOrUpdateFriendLink(saveFriendLinkMO);
        return GraceResult.ok();
    }

    @Override
    public GraceResult getFriendLinkList() {
        List<FriendLinkMO> list = friendLinkService.queryAllFriendList();
        return GraceResult.ok(list);
    }
}
