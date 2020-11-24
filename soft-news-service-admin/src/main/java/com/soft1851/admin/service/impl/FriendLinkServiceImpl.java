package com.soft1851.admin.service.impl;

import com.soft1851.admin.repository.FriendLinkRepository;
import com.soft1851.admin.service.FriendLinkService;
import com.soft1851.mo.FriendLinkMO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName FreindLinkServiceImpl.java
 * @Description TODO
 * @createTime 2020年11月24日 13:32:00
 */
@Service
public class FriendLinkServiceImpl implements FriendLinkService {
    @Autowired
    private FriendLinkRepository friendLinkRepository;

    @Override
    public void saveOrUpdateFriendLink(FriendLinkMO friendLinkMO) {
        friendLinkRepository.save(friendLinkMO);
    }
}
