package com.soft1851.admin.repository;

import com.soft1851.mo.FriendLinkMO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName FriendLinkRepository.java
 * @Description 友链操作
 * @createTime 2020年11月24日 11:32:00
 */
public interface FriendLinkRepository extends MongoRepository<FriendLinkMO,String> {
    /**
     * 根据是否删除字段查询所有友链
     * @param isDelete 是否删除
     * @return
     */
    List<FriendLinkMO> getAllByIsDelete(Integer isDelete);
}
