package com.soft1851.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.soft1851.admin.mapper.AdminUserMapper;
import com.soft1851.admin.service.AdminUserService;
import com.soft1851.bo.NewAdminBO;
import com.soft1851.common.exception.GraceException;
import com.soft1851.common.result.ResponseStatusEnum;
import com.soft1851.common.utils.PageGridResult;
import com.soft1851.pojo.AdminUser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName AdminUserServiceImpl.java
 * @Description TODO
 * @createTime 2020年11月20日 16:42:00
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminUserServiceImpl implements AdminUserService {

    public final AdminUserMapper adminUserMapper;
    public final Sid sid;

    @Override
    public AdminUser queryAdminByUsername(String username) {
        Example adminUserExample = new Example(AdminUser.class);
        Example.Criteria adminUserCriteria = adminUserExample.createCriteria();
        adminUserCriteria.andEqualTo("username", username);
        return adminUserMapper.selectOneByExample(adminUserExample);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createAdminUser(NewAdminBO newAdminBO) {
        String adminId = sid.nextShort();
        AdminUser adminUser = new AdminUser();
        adminUser.setId(adminId);
        adminUser.setUsername(newAdminBO.getUsername());
        adminUser.setAdminName(newAdminBO.getAdminName());
        System.out.println("newAdminBO.getPassword()" + newAdminBO.getPassword());
        //如果密码不为空，则需要加密密码，然后存入数据库
        if (StringUtils.isNotBlank(newAdminBO.getPassword())) {
            String pwd = BCrypt.hashpw(newAdminBO.getPassword(), BCrypt.gensalt());
            System.out.println("pwd" + pwd);
            adminUser.setPassword(pwd);
        }
        // 如果人脸上传以后，则有faceId,需要和admin信息关联存储入库
        if (StringUtils.isNotBlank(newAdminBO.getFaceId())) {
            adminUser.setFaceId(newAdminBO.getFaceId());
        }
        adminUser.setCreatedTime(new Date());
        adminUser.setUpdatedTime(new Date());
        System.out.println("adminUser" + adminUser.toString());
        int result = adminUserMapper.insert(adminUser);
        if (result != 1) {
            GraceException.display(ResponseStatusEnum.ADMIN_NOT_EXIT_ERROR);
        }
    }

    @Override
    public PageGridResult queryAdminList(Integer page, Integer pageSize) {
        Example adminExample = new Example(AdminUser.class);
        adminExample.orderBy("createdTime").desc();

        PageHelper.startPage(page, pageSize);
        List<AdminUser> adminUserList = adminUserMapper.selectByExample(adminExample);

        return setterPagedGrid(adminUserList, page);
    }

    @Override
    public void updateAdmin(String username, String faceId) {
        AdminUser adminUser = queryAdminByUsername(username);
        adminUser.setFaceId(faceId);
        adminUserMapper.updateByPrimaryKey(adminUser);
    }

    private PageGridResult setterPagedGrid(List<?> adminUserList, Integer page) {
        PageInfo<?> pageList = new PageInfo<>(adminUserList);
        PageGridResult gridResult = new PageGridResult();
        gridResult.setRows(adminUserList);
        gridResult.setPage(page);
        gridResult.setRecord(pageList.getPages());
        gridResult.setTotal(pageList.getTotal());
        return gridResult;
    }
}
