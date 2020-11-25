package com.soft1851.article.mapper;

import com.soft1851.api.my.mapper.MyMapper;
import com.soft1851.pojo.Article;
import org.springframework.stereotype.Repository;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName ArticleMapperCustom.java
 * @Description 自定义文章mapper，个性化功能要求
 * @createTime 2020年11月25日 14:07:00
 */
@Repository
public interface ArticleMapperCustom extends MyMapper<Article> {

    /**
     * 更新文章发布状态——》定时-——》即时发布
     */
    void updateAppointToPublish();
}
