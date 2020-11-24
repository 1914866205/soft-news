package com.soft1851.common.enums;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName YesOrNo.java
 * @Description TODO
 * @createTime 2020年11月24日 19:17:00
 */

/**
 * @Desc: 是否 枚举
 */
public enum YesOrNo {
    NO(0,"否"),
    YES(1,"是");
    public final Integer type;
    public final String value;

    YesOrNo(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
