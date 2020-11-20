package com.soft1851.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName NewAdminBO.java
 * @Description TODO
 * @createTime 2020年11月20日 18:46:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewAdminBO {
    private String username;
    private String adminName;
    private String password;
    private String confirmationPassword;
    private String img64;
    private String faceId;
}
