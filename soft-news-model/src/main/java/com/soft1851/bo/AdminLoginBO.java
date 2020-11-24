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
@Builder
public class AdminLoginBO {
    private String username;
    private String password;
    private String img64;

    public AdminLoginBO(String username, String password, String img64) {
        this.username = username;
        this.password = password;
        this.img64 = img64;
    }

    public AdminLoginBO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImg64() {
        return img64;
    }

    public void setImg64(String img64) {
        this.img64 = img64;
    }
}
