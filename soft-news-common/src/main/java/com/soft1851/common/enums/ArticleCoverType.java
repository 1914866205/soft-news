package com.soft1851.common.enums;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName ArticleCoverType.java
 * @Description TODO
 * @createTime 2020年11月24日 19:10:00
 */

/**
 * @Desc:文章封面类型 枚举
 */
public enum ArticleCoverType {

    ONE_IMAGE(1, "单图"),
    WPRDS(2, "纯文字");

    public final Integer type;
    public final String value;

    ArticleCoverType(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
