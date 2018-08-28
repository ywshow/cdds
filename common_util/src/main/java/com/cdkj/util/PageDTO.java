/**
 * project name:sdp-base
 * file name:Page
 * package name:com.cdkj.common.util
 * date:2016/7/12 23:08
 * author:haing
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.util;

import java.util.List;

/**
 * description: 分页数据, 分页参数, 查询参数的 数据传输类 <br>
 * date: 2016/7/12 23:08
 *
 * @author haing
 * @version 1.0
 * @since JDK 1.8
 */
public class PageDTO<E> {
    /**
     * 总页数
     */
    public Integer totalPage;
    /**
     * 查询元素
     */
    public E params;
    /**
     * 列表数据
     */
    public List<?> rows;
    /**
     * 当前页码（入参）
     **/
    private int pageNumber = 1;
    /**
     * 每页显示数量（入参）
     **/
    private int pageSize = 10;
    /**
     * 总数
     **/
    private int total;
    /**
     * 开始页码
     **/
    private int startPage;
    /**
     * 结束页码
     **/
    private int endPage;

    private int startPageMySQL;

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public E getParams() {
        return params;
    }

    public void setParams(E params) {
        this.params = params;
    }

    public Integer getTotalPage() {
        int total = this.getTotal();
        int pageSize = this.getPageSize();
        if (total % pageSize == 0) {
            return total / pageSize;
        } else {
            return total / pageSize + 1;
        }
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageNumber() {
        return pageNumber < 1 ? 1 : pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStartPage() {
        return startPage > 0 ? startPage : (this.getPageNumber() - 1) * this.getPageSize() + 1;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage > 0 ? endPage : this.getPageNumber() * this.getPageSize();
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getStartPageMySQL() {
        return startPage > 0 ? startPage : (this.getPageNumber() - 1) * this.getPageSize();
    }

    public void setStartPageMySQL(int startPageMySQL) {
        this.startPageMySQL = startPageMySQL;
    }
}