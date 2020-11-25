package com.soft1851.article.service;

import com.soft1851.bo.NewArticleBO;
import com.soft1851.pojo.Category;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName ArticleService.java
 * @Description TODO
 * @createTime 2020年11月24日 18:46:00
 */
public interface ArticleService {

    /**
     * 发布文章
     *
     * @param newArticleBO 新建文章BO类
     * @param category     分类
     */
    void createArticle(NewArticleBO newArticleBO, Category category);


    /**
     * 更改文章状态
     * @param articleId 文章id
     * @param pendingStatus 发布状态
     */
    void updateArticleStatus(String articleId, Integer pendingStatus);

    /**
     * 更新定时发布为即使发布
     */
    void updateAppointToPublish();

}
