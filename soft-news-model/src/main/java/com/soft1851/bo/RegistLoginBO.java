package com.soft1851.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName RegistLoginBo.java
 * @Description TODO
 * @createTime 2020年11月16日 13:32:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistLoginBO {

    /**
     * @description: @NotNull只校验null值，@NotBlank会同时校验null和""串
     */
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @NotBlank(message = "短信验证码不能为空")
    private String smsCode;
}
