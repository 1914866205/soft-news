package com.soft1851.common.enums;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName ArticleReviewLevel.java
 * @Description TODO
 * @createTime 2020年11月20日 15:23:00
 */


public enum ArticleReviewLevel {
    PASS("pass", "自动审核通过"),
    BLOCK("block", "自动审核不通过"),
    REVIEW("review", "建议人工复审");
    public final String type;
    public final String value;
    ArticleReviewLevel(String type, String value) {
        this.type = type;
        this.value = value;
    }

}
