/**
 * project name:sdp-base
 * file name:MyPropertyPreFilter
 * package name:com.cdkj.common.util
 * date:2016/8/16 18:24
 * author:haing
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.Map;

/**
 * description: //重写SimplePropertyPreFilter，处理多重对象时，过滤不需要的字段 <br>
 * date: 2016/8/16 18:24
 *
 * @author haing
 * @version 1.0
 * @since JDK 1.8
 */
public class MyPropertyPreFilter implements PropertyPreFilter {
    static {
        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
    }

    private Map<Class<?>, String[]> includes = null;//结果包含的字段
    private Map<Class<?>, String[]> excludes = null;//结果排除的字段

    public MyPropertyPreFilter() {
    }

    public MyPropertyPreFilter(Map<Class<?>, String[]> includes) {
        super();
        this.includes = includes;
    }

    public MyPropertyPreFilter(Map<Class<?>, String[]> includes, Map<Class<?>, String[]> excludes) {
        super();
        this.excludes = excludes;
    }

    /*
     * 此方法有两个参数，第一个是要查找的字符串数组，第二个是要查找的字符或字符串
     */
    public static boolean isHave(String[] strs, String s) {
        for (int i = 0; i < strs.length; i++) {
            // 循环查找字符串数组中的每个字符串中是否包含所有查找的内容
            if (strs[i].equals(s)) {
                // 查找到了就返回真，不在继续查询
                return true;
            }
        }
        // 没找到返回false
        return false;
    }

    /**
     * @param serializer
     * @param source
     * @param name
     * @return 返回值：true表示序列化；false表示不序列化
     */
    @Override
    public boolean apply(JSONSerializer serializer, Object source, String name) {
        //对象为空。直接放行
        if (source == null) {
            return true;
        }

        // 获取当前需要序列化的对象的类对象
        Class<?> clazz = source.getClass();

        // 无需序列的对象、寻找需要过滤的对象，可以提高查找层级
        // 找到不需要的序列化的类型
        if (ValidateUtils.isNotEmptyMap(excludes)) {
            for (Map.Entry<Class<?>, String[]> item : this.excludes.entrySet()) {
                // isAssignableFrom()，用来判断类型间是否有继承关系
                if (item.getKey().isAssignableFrom(clazz)) {
                    String[] strs = item.getValue();
                    // 该类型下 此 name 值无需序列化
                    if (isHave(strs, name)) {
                        return false;
                    }
                }
            }
        }

        // 需要序列的对象集合为空 表示 全部需要序列化
        if (ValidateUtils.isEmptyMap(this.includes)) {
            return true;
        }

        // 需要序列的对象
        // 找到不需要的序列化的类型
        for (Map.Entry<Class<?>, String[]> item : this.includes.entrySet()) {
            // isAssignableFrom()，用来判断类型间是否有继承关系
            if (item.getKey().isAssignableFrom(clazz)) {
                String[] strs = item.getValue();
                // 该类型下 此 name 值无需序列化
                if (isHave(strs, name)) {
                    return true;
                }
            }
        }

        //是map的子类，直接放过
        if (Map.class.isAssignableFrom(clazz)) {
            return true;
        }

        return false;
    }

    public Map<Class<?>, String[]> getIncludes() {
        return includes;
    }

    public void setIncludes(Map<Class<?>, String[]> includes) {
        this.includes = includes;
    }

    public Map<Class<?>, String[]> getExcludes() {
        return excludes;
    }

    public void setExcludes(Map<Class<?>, String[]> excludes) {
        this.excludes = excludes;
    }
}