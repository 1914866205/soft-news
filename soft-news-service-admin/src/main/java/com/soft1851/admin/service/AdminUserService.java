package com.soft1851.admin.service;

import com.soft1851.bo.NewAdminBO;
import com.soft1851.common.utils.PageGridResult;
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
     *
     * @param username 用户名
     * @return
     */
    AdminUser queryAdminByUsername(String username);

    /**
     * 新增管理员
     *
     * @param newAdminBO BO入参
     */
    void createAdminUser(NewAdminBO newAdminBO);


    /**
     * 分页查询管理员列表
     * @param page
     * @param pageSize
     * @return
     */
    PageGridResult queryAdminList(Integer page, Integer pageSize);
}
