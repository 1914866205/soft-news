package com.soft1851.user.service;

import com.soft1851.pojo.AppUser;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName UserService.java
 * @Description TODO
 * @createTime 2020年11月16日 16:23:00
 */
public interface UserService {
    /**
     * 判断用户是否存在，如果存在返回user信息
     *
     * @param mobile 手机号
     * @return
     */
    AppUser queryMobileIsExist(String mobile);

    /**
     * 创建用户，新增用户记录到数据库
     *
     * @param mobile 手机号
     * @return
     */
    AppUser createUser(String mobile);
}
