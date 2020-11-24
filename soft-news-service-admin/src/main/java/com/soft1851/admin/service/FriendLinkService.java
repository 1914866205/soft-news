package com.soft1851.admin.service;

import com.soft1851.mo.FriendLinkMO;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName FriendLinkService.java
 * @Description TODO
 * @createTime 2020年11月24日 13:31:00
 */
public interface FriendLinkService {
    /**
     * 新增或者更新友链
     * @param friendLinkMO 入参
     */
    void saveOrUpdateFriendLink(FriendLinkMO friendLinkMO);
}
