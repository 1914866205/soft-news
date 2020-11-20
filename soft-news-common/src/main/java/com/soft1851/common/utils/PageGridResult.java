package com.soft1851.common.utils;

import java.util.List;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName PageGridResult.java
 * @Description TODO
 * @createTime 2020年11月20日 19:40:00
 */
public class PageGridResult {
    /**
     * 当前页
     */
    private int page;

    /**
     * 总页数
     */
    private long total;

    /**
     * 总记录数
     */
    private long record;

    /**
     * 每行显示的内容
     */
    private List<?> rows;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getRecord() {
        return record;
    }

    public void setRecord(long record) {
        this.record = record;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
