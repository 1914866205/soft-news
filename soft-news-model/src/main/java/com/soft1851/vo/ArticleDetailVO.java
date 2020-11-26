package com.soft1851.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName ArticleDetailVO.java
 * @Description TODO
 * @createTime 2020年11月26日 17:45:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDetailVO {
    private String id;
    private String title;
    private String cover;
    private Integer categoryId;
    private String categoryName;
    private String publishUserId;
    private Date publishTime;
    private String content;
    private String publishUserName;
    private Integer readCounts;

}


