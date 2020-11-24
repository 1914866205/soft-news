package com.soft1851.bo;

import com.soft1851.validate.CheckUrl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName SaveFriendLinkBO.java
 * @Description TODO
 * @createTime 2020年11月24日 13:44:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveFriendLinkBO {

    private String id;
    @NotBlank(message = "友情链接名不能为空")
    private String linkName;

    @NotBlank(message = "友情链接地址不能为空")
    @CheckUrl
    private String linkUrl;
    @NotNull(message = "请选择保留或删除")
    private Integer isDelete;
}
