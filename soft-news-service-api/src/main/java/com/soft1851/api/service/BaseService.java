package com.soft1851.api.service;

import com.github.pagehelper.PageInfo;
import com.soft1851.common.utils.PageGridResult;
import com.soft1851.common.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName BaseService.java
 * @Description TODO
 * @createTime 2020年11月26日 15:08:00
 */
public class BaseService {
    public static final String REDIS_ALL_CATEGORY = "redis_all_category";
    public static final String REDIS_WRITER_FANS_COUNTS = "redis_writer_fans_counts";
    public static final String REDIS_MY_FOLLOW_COUNTS = "redis_my_follow_counts";
    public static final String REDIS_ARTICLE_COMMENT_COUNTS = "redis_article_comment_counts";
    @Autowired
    public RedisOperator redis;

    public PageGridResult setterPageGrid(List<?> list, Integer page) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PageGridResult gridResult = new PageGridResult();
        gridResult.setRows(list);
        gridResult.setPage(page);
        gridResult.setRecord(pageList.getTotal());
        gridResult.setTotal(pageList.getPages());
        return gridResult;
    }
}

