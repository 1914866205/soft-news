package com.soft1851.article.controller;

import com.soft1851.api.BaseController;
import com.soft1851.api.controller.category.CategoryControllerApi;
import com.soft1851.article.service.CategoryService;
import com.soft1851.common.result.GraceResult;
import com.soft1851.common.utils.JsonUtil;
import com.soft1851.pojo.Category;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName CategoryController.java
 * @Description 文章分类实现接口
 * @createTime 2020年11月24日 18:45:00
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired()))
public class CategoryController extends BaseController implements CategoryControllerApi {

    private final CategoryService categoryService;

    @Override
    public GraceResult getAll() {
        //先从redis中查询，如果有，则返回，否则为空。则查询数据库后先放缓存，再返回
        String allCategoryJson = redis.get(REDIS_ALL_CATEGORY);
        List<Category> categoryList;
        System.out.println("allCategoryJson" + allCategoryJson);
        System.out.println(StringUtils.isBlank(allCategoryJson));
        if (StringUtils.isBlank(allCategoryJson)) {
            // 如果redis没有数据，则从数据库中查询文章分类
            categoryList = categoryService.selectAll();
            //存入redis
            redis.set(REDIS_ALL_CATEGORY, JsonUtil.objectToJson(categoryList));
            allCategoryJson = redis.get(REDIS_ALL_CATEGORY);
            categoryList = JsonUtil.jsonToList(allCategoryJson, Category.class);
        } else {
            // 否则,redis有数据，则直接转化为list返回，保证减少数据库压力
            categoryList = JsonUtil.jsonToList(allCategoryJson, Category.class);
        }
        System.out.println("categoryList：" + categoryList);
        return GraceResult.ok(categoryList);
    }
}
