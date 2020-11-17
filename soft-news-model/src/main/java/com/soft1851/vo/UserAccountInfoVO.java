package com.soft1851.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName UserAccountInfoVO.java
 * @Description TODO
 * @createTime 2020年11月17日 10:18:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAccountInfoVO {
    private String id;
    private String mobile;
    private String nickname;
    private String face;
    private String realname;
    private String email;
    private Integer sex;
    private Date birthday;
    private String province;
    private String city;
    private String district;
}
