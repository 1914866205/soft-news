package com.soft1851.user.service.impl;

import com.soft1851.common.enums.UserStatus;
import com.soft1851.common.utils.DateUtil;
import com.soft1851.common.utils.DesensitizationUtil;
import com.soft1851.common.utils.RedisOperator;
import com.soft1851.pojo.AppUser;
import com.soft1851.user.mapper.AppUserMapper;
import com.soft1851.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName UserServiceImpl.java
 * @Description TODO
 * @createTime 2020年11月16日 16:23:00
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    public final AppUserMapper appUserMapper;
    public final RedisOperator redis;
    @Resource
    private Sid sid;

    public static final String REDIS_USER_INFO = "redis_user_info";
    public static final String USER_FACE = "https://avatars3.githubusercontent.com/u/58495771?s=460&u=bb2f820d0cb11cf18fa5a2c787261db55023c0cc&v=4";


    @Override
    public AppUser queryMobileIsExist(String mobile) {
        Example userExample = new Example(AppUser.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andEqualTo("mobile", mobile);
        return appUserMapper.selectOneByExample(userExample);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AppUser createUser(String mobile) {
        //若分库分表，数据库表主键id必须保证全局唯一，不得重复
        String userId = sid.nextShort();
        //构建用户对象
        AppUser user = AppUser.builder()
                .id(userId)
                .mobile(mobile)
                .nickname("用户:" + DesensitizationUtil.commonDisplay(mobile))
                .face(USER_FACE)
                .birthday(DateUtil.stringToDate("2000-01-01"))
                .activeStatus(UserStatus.INACTIVE.type)
                .totalIncome(0)
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();
        //执行插入方法
        appUserMapper.insert(user);
        return user;
    }
}
