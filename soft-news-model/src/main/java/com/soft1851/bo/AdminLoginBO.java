package com.soft1851.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName AdminLoginBO.java
 * @Description TODO
 * @createTime 2020年11月20日 16:49:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminLoginBO {
    private String username;
    private String password;
    private String img64;

}
