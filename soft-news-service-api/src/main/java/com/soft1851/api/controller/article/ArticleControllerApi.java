package com.soft1851.api.controller.article;

import com.soft1851.bo.NewArticleBO;
import com.soft1851.common.result.GraceResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
     *
     * @param newArticleBO 文章BO类
     * @param result       校验结果
     * @return
     */
    @ApiOperation(value = "用户发文", notes = "用户发文", httpMethod = "POST")
    @PostMapping("createArticle")
    GraceResult createArticle(@RequestBody @Valid NewArticleBO newArticleBO, BindingResult result);

    /**
     * 管理员审核文章
     *
     * @param articleId
     * @param passOrNot
     * @return
     */
    @PostMapping("/doReview")
    @ApiOperation(value = "管理员审核文章", notes = "管理员审核文章", httpMethod = "POST")
    GraceResult doReview(@RequestParam String articleId, @RequestParam Integer passOrNot);


    /**
     * 用户撤回文章
     * @param userId
     * @param articleId
     * @return
     */
    @PostMapping("/withdraw")
    @ApiOperation(value = "用户撤回文章", notes = "用户撤回文章", httpMethod = "POST")
    GraceResult withdraw(@RequestParam String userId, @RequestParam String articleId);

    /**
     * 用户删除文章
     * @param userId
     * @param articleId
     * @return
     */
    @PostMapping("/delete")
    @ApiOperation(value = "用户删除文章", notes = "用户删除文章", httpMethod = "POST")
    GraceResult delete(@RequestParam String userId, @RequestParam String articleId);



    /**
     * 查询文章详情
     * @param articleId 文章id
     * @return
     */
    @GetMapping("detail")
    @ApiOperation(value = "文章详情查询", notes = "文章详情查询", httpMethod = "GET")
    GraceResult queryDetail(@RequestParam String articleId);

}
