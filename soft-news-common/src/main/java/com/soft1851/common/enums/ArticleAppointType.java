package com.soft1851.common.enums;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName ArticleAppointType.java
 * @Description TODO
 * @createTime 2020年11月24日 19:05:00
 */

/**
 * @Desc: 文章发布操作类型 枚举
 */
public enum ArticleAppointType {
    TIMING(1, "文章定时发布——定时"),
    IMMEDIATELY(0, "文章立即发布——即使");

    public final Integer type;
    public final String value;

    ArticleAppointType(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
