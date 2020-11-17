package com.soft1851.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.sql.Date;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName UpdateUserInfoBO.java
 * @Description TODO
 * @createTime 2020年11月17日 11:01:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserInfoBO {

    @NotBlank(message = "用户ID不能为空")
    private String id;

    @NotBlank(message = "用户昵称不能为空")
    @Length(max = 12, message = "用户昵称不能超过为12位")
    private String nickname;

    @NotBlank(message = "用户头像不能为空")
    private String face;

    @NotBlank(message = "真实姓名不能为空")
    private String realname;

    @Email
    @NotBlank(message = "邮件不能为空")
    private String email;

    @NotNull(message = "请选择一个性别")
    @Min(value = 0, message = "性别选择不正确")
    @Max(value = 1, message = "性别选择不正确")
    private Integer sex;
    /**
     * 前端日期字符串传到后端，转为Date类型
     */
    @NotNull(message = "请选择出生日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date birthday;
    @NotBlank(message = "请选择所在城市")
    private String provice;
    @NotBlank(message = "请选择所在城市")
    private String city;
    @NotBlank(message = "请选择所在城市")
    private String district;


}
