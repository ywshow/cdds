/**
 * project name:cdds
 * file name:ResultInfo
 * package name:com.cdkj.util
 * date:2018/7/25 9:41
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * description: 同一返回结果格式 <br>
 * date: 2018/7/25 9:41
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
public class ResultInfo<T> {

    /**
     * 对象返回
     */
    private T record;

    /**
     * map返回
     */
    private Map<String, Object> map;

    /**
     * 分页返回
     */
    private PageInfo pageInfo;

    public ResultInfo() {
    }

    public ResultInfo(T record) {
        this.record = record;
    }

    public ResultInfo(Map<String, Object> map) {
        this.map = map;
    }

    public ResultInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public ResultInfo(T record, Map<String, Object> map, PageInfo pageInfo) {
        this.record = record;
        this.map = map;
        this.pageInfo = pageInfo;
    }

    public ResultInfo(Page page, List<T> list) {
        pageInfo = page.toPageInfo();
        pageInfo.setList(list);
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public T getRecord() {
        return record;
    }

    public void setRecord(T record) {
        this.record = record;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}