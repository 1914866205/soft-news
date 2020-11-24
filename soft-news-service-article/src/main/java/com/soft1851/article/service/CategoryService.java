package com.soft1851.article.service;

import com.soft1851.pojo.Category;

import java.util.List;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName CategoryService.java
 * @Description TODO
 * @createTime 2020年11月24日 18:47:00
 */
public interface CategoryService {
    /**
     * 查询所有文章分类
     * @return
     */
    List<Category> selectAll();
}
