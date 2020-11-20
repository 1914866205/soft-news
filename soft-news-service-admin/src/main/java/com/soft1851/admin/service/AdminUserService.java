package com.soft1851.admin.service;

import com.soft1851.pojo.AdminUser;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName AdminUserService.java
 * @Description TODO
 * @createTime 2020年11月20日 16:41:00
 */
public interface AdminUserService {

    /**
     * 获得管理员用户信息
     * @param username 用户名
     * @return
     */
    AdminUser queryAdminByUsername(String username);
}
