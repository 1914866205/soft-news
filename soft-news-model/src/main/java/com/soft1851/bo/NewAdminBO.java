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
@Builder
public class NewAdminBO {
    private String username;
    private String adminName;
    private String password;
    private String confirmationPassword;
    private String img64;
    private String faceId;

    public NewAdminBO(String username, String adminName, String password, String confirmationPassword, String img64, String faceId) {
        this.username = username;
        this.adminName = adminName;
        this.password = password;
        this.confirmationPassword = confirmationPassword;
        this.img64 = img64;
        this.faceId = faceId;
    }

    public NewAdminBO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmationPassword() {
        return confirmationPassword;
    }

    public void setConfirmationPassword(String confirmationPassword) {
        this.confirmationPassword = confirmationPassword;
    }

    public String getImg64() {
        return img64;
    }

    public void setImg64(String img64) {
        this.img64 = img64;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }
}
