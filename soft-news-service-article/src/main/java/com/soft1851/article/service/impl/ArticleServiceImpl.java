package com.soft1851.article.service.impl;

import com.soft1851.article.mapper.ArticleMapper;
import com.soft1851.article.service.ArticleService;
import com.soft1851.bo.NewArticleBO;
import com.soft1851.common.enums.ArticleAppointType;
import com.soft1851.common.enums.ArticleReviewStatus;
import com.soft1851.common.enums.YesOrNo;
import com.soft1851.common.exception.GraceException;
import com.soft1851.common.result.ResponseStatusEnum;
import com.soft1851.pojo.Article;
import com.soft1851.pojo.Category;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName ArticleServiceImpl.java
 * @Description TODO
 * @createTime 2020年11月24日 18:47:00
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleServiceImpl implements ArticleService {
    private final ArticleMapper articleMapper;
    private final Sid sid;

    @Override
    public void createArticle(NewArticleBO newArticleBO, Category category) {
        System.out.println("newArticleBO"+newArticleBO);
        System.out.println("category"+category);
        String articleId = sid.nextShort();
        Article article = new Article();


        BeanUtils.copyProperties(newArticleBO, article);
        article.setId(articleId);
        article.setCategoryId(category.getId());
        article.setArticleStatus(ArticleReviewStatus.REVIEWING.type);
        article.setCommentCounts(0);
        article.setReadCounts(0);
        article.setIsDelete(YesOrNo.NO.type);
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());
        if (article.getIsAppoint().equals(ArticleAppointType.TIMING.type)){
            article.setPublishTime(newArticleBO.getPublishTime());
        } else if (article.getIsAppoint().equals(ArticleAppointType.IMMEDIATELY.type)) {
            article.setPublishTime(new Date());
        }

        int res = articleMapper.insert(article);
        if (res != 1) {
            GraceException.display(ResponseStatusEnum.ARTICLE_CREATE_ERROR);
        }
        //后续通过阿里智能AI实现对文章文本的自动检测，自动审核
    }
}
