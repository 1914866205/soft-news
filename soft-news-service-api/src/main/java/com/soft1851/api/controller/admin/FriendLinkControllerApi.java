package com.soft1851.api.controller.admin;

import com.soft1851.bo.SaveFriendLinkBO;
import com.soft1851.common.result.GraceResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName FriendLinkController.java
 * @Description TODO
 * @createTime 2020年11月24日 13:35:00
 */
@Api(value = "首页友情链接维护", tags = {"首页友情链接维护controller"})
@RequestMapping("friendLink")
public interface FriendLinkControllerApi {

    @ApiOperation(value = "新增或者修改友情链接", notes = "新增或者修改友情链接", httpMethod = "POST")
    @PostMapping("/saveOrUpdateFriendLink")
    GraceResult saveOrUpdateFriendLink(@RequestBody @Valid SaveFriendLinkBO saveFriendLinkBO, BindingResult result);

    @ApiOperation(value = "查询友情链接列表", notes = "查询友情链接列表", httpMethod = "POST")
    @PostMapping("/getFriendLinkList")
    GraceResult getFriendLinkList();

    
}

