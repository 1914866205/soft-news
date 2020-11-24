package com.soft1851.pojo;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName Category.java
 * @Description TODO
 * @createTime 2020年11月24日 18:31:00
 */
public class Category {
    @Id
    private Integer id;

    /**
     * 分类名
     */
    private String name;

    /**
     * 标签颜色
     */
    @Column(name = "tag_color")
    private String tagColor;

    public Category() {
    }

    public Category(Integer id, String name, String tagColor) {
        this.id = id;
        this.name = name;
        this.tagColor = tagColor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagColor() {
        return tagColor;
    }

    public void setTagColor(String tagColor) {
        this.tagColor = tagColor;
    }
}
