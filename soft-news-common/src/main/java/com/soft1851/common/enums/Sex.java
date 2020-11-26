package com.soft1851.common.enums;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName Sex.java
 * @Description TODO
 * @createTime 2020年11月26日 16:24:00
 */

/**
 * @Desc 性别枚举类
 */
public enum Sex {
    /**
     * 性别枚举类
     */
    man(1, "男"),
    woman(0, "女");
    public final Integer type;
    public final String value;

    Sex(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
