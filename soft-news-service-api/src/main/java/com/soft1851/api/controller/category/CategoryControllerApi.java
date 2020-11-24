package com.soft1851.api.controller.category;

import com.soft1851.common.result.GraceResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName CategoryControllerApi.java
 * @Description TODO
 * @createTime 2020年11月24日 19:34:00
 */
@Api(value = "文章类型的controller", tags = {"文章类型的controller"})
@RequestMapping("category")
public interface CategoryControllerApi {
    /**
     * 查询文章分类
     *
     * @return
     */
    @GetMapping("/getAll")
    @ApiOperation(value = "查询文章分类", notes = "查询文章分类", httpMethod = "GET")
    GraceResult getAll();

}
