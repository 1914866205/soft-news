package com.soft1851.article.service.impl;

import com.soft1851.article.mapper.ArticleMapper;
import com.soft1851.article.mapper.ArticleMapperCustom;
import com.soft1851.article.service.ArticleService;
import com.soft1851.bo.NewArticleBO;
import com.soft1851.common.enums.ArticleAppointType;
import com.soft1851.common.enums.ArticleReviewLevel;
import com.soft1851.common.enums.ArticleReviewStatus;
import com.soft1851.common.enums.YesOrNo;
import com.soft1851.common.exception.GraceException;
import com.soft1851.common.result.ResponseStatusEnum;
import com.soft1851.common.utils.AliTextReviewUtil;
import com.soft1851.pojo.Article;
import com.soft1851.pojo.Category;
import com.soft1851.vo.ArticleDetailVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName ArticleServiceImpl.java
 * @Description TODO
 * @createTime 2020年11月24日 18:47:00
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleServiceImpl implements ArticleService {
    private final ArticleMapper articleMapper;
    private final ArticleMapperCustom articleMapperCustom;
    private final AliTextReviewUtil aliTextReviewUtil;

    private final Sid sid;

    @Override
    public void createArticle(NewArticleBO newArticleBO, Category category) {
//        System.out.println("newArticleBO"+newArticleBO);
//        System.out.println("category"+category);
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
        if (article.getIsAppoint().equals(ArticleAppointType.TIMING.type)) {
            article.setPublishTime(newArticleBO.getPublishTime());
        } else if (article.getIsAppoint().equals(ArticleAppointType.IMMEDIATELY.type)) {
            article.setPublishTime(new Date());
        }
        System.out.println("*************************");
        System.out.println(article.getIsAppoint());
        System.out.println(ArticleAppointType.TIMING.type);
        System.out.println(ArticleAppointType.IMMEDIATELY.type);
        System.out.println(article.getIsAppoint().equals(ArticleAppointType.TIMING.type));
        System.out.println(article.getIsAppoint().equals(ArticleAppointType.IMMEDIATELY.type));
        System.out.println(article.getPublishTime());
        System.out.println("*************************");
//        System.out.println("要插入的article"+article);
        int res = articleMapper.insert(article);
        if (res != 1) {
            GraceException.display(ResponseStatusEnum.ARTICLE_CREATE_ERROR);
        }
        //后续通过阿里智能AI实现对文章文本的自动检测，自动审核
        //通过阿里智能AI实现对文章文本的字段检测
        System.out.println("newArticleBO.getTitle() + newArticleBO.getContent()" + newArticleBO.getTitle() + newArticleBO.getContent());
        String reviewResult = aliTextReviewUtil.reviewTextContent(newArticleBO.getTitle() + newArticleBO.getContent());
        log.info("审核结果" + reviewResult);
        if (ArticleReviewLevel.PASS.type.equalsIgnoreCase(reviewResult)) {
            log.info("审核通过");
            //修改文章状态为：审核通过
            this.updateArticleStatus(articleId, ArticleReviewStatus.SUCCESS.type);
        } else if (ArticleReviewLevel.REVIEW.type.equalsIgnoreCase(reviewResult)) {
            log.info("需要人工复查");
            //修改文章状态为：需要人工复查
            this.updateArticleStatus(articleId, ArticleReviewStatus.WAITING_MANUAL.type);
        } else if (ArticleReviewLevel.BLOCK.type.equalsIgnoreCase(reviewResult)) {
            log.info("审核不通过");
            //修改文章状态为审核不通过
            this.updateArticleStatus(articleId, ArticleReviewStatus.FAILED.type);
        }
    }


    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void updateAppointToPublish() {
        // 方法体在mapper.xml里
        articleMapperCustom.updateAppointToPublish();
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void updateArticleStatus(String articleId, Integer pendingStatus) {
        Example example = new Example(Article.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", articleId);
        Article pendingArticle = new Article();
        pendingArticle.setArticleStatus(pendingStatus);

        int res = articleMapper.updateByExampleSelective(pendingArticle, example);
        if (res != 1) {
            GraceException.display(ResponseStatusEnum.ARTICLE_REVIEW_ERROR);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void deleteArticle(String userId, String articleId) {
        Example articleExample = makeExampleCriteria(userId, articleId);
        Article pending = new Article();
        pending.setIsDelete(YesOrNo.YES.type);

        int result = articleMapper.updateByExampleSelective(pending, articleExample);
        if (result != 1) {
            GraceException.display(ResponseStatusEnum.ARTICLE_DELETE_ERROR);
        }
    }

    @Override
    public void withdrawArticle(String userId, String articleId) {
        Example articleExample = makeExampleCriteria(userId, articleId);
        Article pending = new Article();
        pending.setIsDelete(ArticleReviewStatus.WAITING_MANUAL.type);
        int result = articleMapper.updateByExampleSelective(pending, articleExample);
        if (result != 1) {
            GraceException.display(ResponseStatusEnum.ARTICLE_WITHDRAW_ERROR);
        }
    }

    private Example makeExampleCriteria(String userId, String articleId) {
        Example articleExample = new Example(Article.class);
        Example.Criteria criteria = articleExample.createCriteria();
        criteria.andEqualTo("publishUserId", userId);
        criteria.andEqualTo("id", articleId);
        return articleExample;
    }

    @Override
    public ArticleDetailVO queryDetail(String articleId) {
        Article article = new Article();
        article.setId(articleId);
        article.setIsAppoint(YesOrNo.NO.type);
        article.setIsDelete(YesOrNo.NO.type);
        article.setArticleStatus(ArticleReviewStatus.SUCCESS.type);
        Article result = articleMapper.selectByPrimaryKey(articleId);
        ArticleDetailVO detailVO = new ArticleDetailVO();
        BeanUtils.copyProperties(result, detailVO);
        detailVO.setCover(result.getArticleCover());
        return detailVO;
    }
}
