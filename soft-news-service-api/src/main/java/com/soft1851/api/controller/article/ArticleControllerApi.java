package com.soft1851.api.controller.article;

import com.soft1851.bo.NewArticleBO;
import com.soft1851.common.result.GraceResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName ArticleControllerApi.java
 * @Description TODO
 * @createTime 2020年11月24日 19:38:00
 */
@Api(value = "文章业务的controller", tags = {"文章业务的controller"})
@RequestMapping("article")
public interface ArticleControllerApi {
    /**
     * 用户发文章
     * @param newArticleBO 文章BO类
     * @param result 校验结果
     * @return
     */
    @ApiOperation(value = "用户发文", notes = "用户发文", httpMethod = "POST")
    @PostMapping("createArticle")
    GraceResult createArticle(@RequestBody @Valid NewArticleBO newArticleBO, BindingResult result);


}
