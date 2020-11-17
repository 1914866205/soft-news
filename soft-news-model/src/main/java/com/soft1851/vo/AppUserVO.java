package com.soft1851.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName AppUserVO.java
 * @Description TODO
 * @createTime 2020年11月17日 11:39:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUserVO {
    private String id;
    private String nickname;
    private String face;
    /**
     * 激活状态
     */
    private Integer activeStatus;

    /**
     * 我的关注数
     */
    private Integer myFollowCount;

    /**
     * 我的粉丝数
     */
    private Integer myFansCounts;
}
