package com.soft1851.article.controller;

import com.soft1851.api.BaseController;
import com.soft1851.api.controller.article.ArticleControllerApi;
import com.soft1851.article.service.ArticleService;
import com.soft1851.bo.NewArticleBO;
import com.soft1851.common.enums.ArticleReviewStatus;
import com.soft1851.common.enums.YesOrNo;
import com.soft1851.common.result.GraceResult;
import com.soft1851.common.result.ResponseStatusEnum;
import com.soft1851.common.utils.JsonUtil;
import com.soft1851.pojo.Category;
import com.soft1851.vo.AppUserVO;
import com.soft1851.vo.ArticleDetailVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.*;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName ArticleController.java
 * @Description TODO
 * @createTime 2020年11月24日 18:45:00
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleController extends BaseController implements ArticleControllerApi {
    private final ArticleService articleService;
    private final RestTemplate restTemplate;


    /**
     * @param newArticleBO 文章BO类
     * @param result       校验结果
     * @return
     */
    @Override
    public GraceResult createArticle(@Valid NewArticleBO newArticleBO, BindingResult result) {
        System.out.println(newArticleBO);
        System.out.println(result);
        //判断BingingResult是否保存锁雾的验证信息，如果有，则直接return
        if (result.hasErrors()) {
            Map<String, String> errorMap = getErrors(result);
            return GraceResult.errorMap(errorMap);
        }

        //判断分类id是否存在
        String allCatJson = redis.get(REDIS_ALL_CATEGORY);
        Category temp = null;
        if (StringUtils.isBlank(allCatJson)) {
            return GraceResult.errorCustom(ResponseStatusEnum.SYSTEM_OPERATION_ERROR);
        } else {
            List<Category> categories = JsonUtil.jsonToList(allCatJson, Category.class);
            assert categories != null;
            for (Category c : categories) {
                if (c.getId().equals(newArticleBO.getCategoryId())) {
                    temp = c;
                    break;
                }
            }
            if (temp == null) {
                return GraceResult.errorCustom(ResponseStatusEnum.ARTICLE_CATEGORY_NOT_EXIST_ERROR);
            }
        }
        System.out.println(newArticleBO.toString());
        articleService.createArticle(newArticleBO, temp);
        return GraceResult.ok();
    }


    @Override
    public GraceResult doReview(String articleId, Integer passOrNot) {
        Integer pendingStatus;
        if (passOrNot == YesOrNo.YES.type) {
            pendingStatus = ArticleReviewStatus.SUCCESS.type;
        } else if (YesOrNo.NO.type.equals(passOrNot)) {
            pendingStatus = ArticleReviewStatus.FAILED.type;
        } else {
            return GraceResult.errorCustom(ResponseStatusEnum.ARTICLE_REVIEW_ERROR);
        }

        articleService.updateArticleStatus(articleId, pendingStatus);
        return GraceResult.ok();
    }

    @Override
    public GraceResult withdraw(String userId, String articleId) {
        articleService.withdrawArticle(userId, articleId);
        return GraceResult.ok();
    }

    @Override
    public GraceResult delete(String userId, String articleId) {
        articleService.deleteArticle(userId, articleId);
        return GraceResult.ok();
    }



    /**
     * 查询文章详情
     *
     * @param articleId 文章id
     * @return
     */
    @Override
    public GraceResult queryDetail(String articleId) {
        ArticleDetailVO detailVO = articleService.queryDetail(articleId);
        Set<String> idSet = new HashSet<>();
        idSet.add(detailVO.getPublishUserId());
        List<AppUserVO> publisherList = getPublisherList(idSet);
        if (!publisherList.isEmpty()) {
            detailVO.setPublishUserName(publisherList.get(0).getNickname());
        }
        detailVO.setReadCounts(getCountsFromRedis(REDIS_ARTICLE_READ_COUNTS + ":" + articleId));
        return GraceResult.ok(detailVO);
    }

    /**
     * 发起远程调用，获得用户的基本信息
     *
     * @param idSet id集合
     * @return List<AppUserVO>
     */
    private List<AppUserVO> getPublisherList(Set<String> idSet) {
        String userServerUrlExecute = "http://localhost:8003/user/queryByIds?userIds=" + JsonUtil.objectToJson(idSet);
        ResponseEntity<GraceResult> responseEntity
                = restTemplate.getForEntity(userServerUrlExecute, GraceResult.class);
        GraceResult bodyResult = responseEntity.getBody();
        List<AppUserVO> publisherList = null;
        assert bodyResult != null;
        if (bodyResult.getStatus() == 200) {
            String userJson = JsonUtil.objectToJson(bodyResult.getData());
            publisherList = JsonUtil.jsonToList(userJson, AppUserVO.class);
        }
        return publisherList;
    }



    /**
     * 从redis根据key读取阅读量
     *
     * @param key key
     * @return value
     */
    private Integer getCountsFromRedis(String key) {
        String countsStr = redis.get(key);
        if (StringUtils.isBlank(countsStr)) {
            countsStr = "0";
        }
        return Integer.valueOf(countsStr);
    }
}
