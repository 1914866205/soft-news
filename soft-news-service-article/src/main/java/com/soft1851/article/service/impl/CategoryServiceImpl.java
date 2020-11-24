package com.soft1851.article.service.impl;

import com.soft1851.article.mapper.CategoryMapper;
import com.soft1851.article.service.CategoryService;
import com.soft1851.pojo.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName CategoryServiceImpl.java
 * @Description TODO
 * @createTime 2020年11月24日 18:47:00
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    @Override
    public List<Category> selectAll() {
        System.out.println("categoryMapper.selectAll()"+categoryMapper.selectAll());
        return categoryMapper.selectAll();
    }
}
