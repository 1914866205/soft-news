package com.soft1851.common.enums;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName ArticleReviewStatus.java
 * @Description TODO
 * @createTime 2020年11月24日 19:19:00
 */

/**
 * @Desc: 文章审核状态 枚举
 * 文章状态：
 * 1. 审核中（用户已提交）
 * 2. 机审结束，等待人工审核
 * 3. 审核通过（已发布）
 * 4. 审核未通过
 * 5. 文章撤回
 * <p>
 * 1/2 课堂统一归类为 正在审核 的状态
 */
public enum ArticleReviewStatus {
    REVIEWING(1, "审核中（用户已提交）"),
    WAITING_MANUAL(2, " 机审结束，等待人工审核"),
    SUCCESS(3, "审核通过（已发布）"),
    FAILED(4, "审核未通过"),
    WITHDRAW(5, "文章撤回");

    public final Integer type;
    public final String value;

    ArticleReviewStatus(Integer type, String value) {
        this.type = type;
        this.value = value;
    }


    public static boolean isAvailableStatusValid(Integer tempStatus) {
        if (tempStatus != null) {
            if (tempStatus == REVIEWING.type
                    || tempStatus == WAITING_MANUAL.type
                    || tempStatus == SUCCESS.type
                    || tempStatus == FAILED.type
                    || tempStatus == WITHDRAW.type
            ) {
                return true;
            }
        }
        return false;
    }
}
