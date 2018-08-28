/**
 * project name:utils-common
 * file name:SortList
 * package name:com.cdkj.common.util
 * date:2016-12-20 16:43
 * author:kola
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * description: java中List对象排序通用方法//TODO <br>
 * date: 2016-12-20 16:43
 *
 * @author kola
 * @version 1.0
 * @since JDK 1.8
 */
public class SortList<T> {
    /**
     * 根据List某字段进行排序
     *
     * @param list   需要排序的List
     * @param method 排序的字段
     * @param sort   desc 倒序  不传为正序
     */
    public void Sort(List<T> list, final String method, final String sort) {
        Collections.sort(list, new Comparator() {
            public int compare(Object a, Object b) {
                int ret = 0;
                try {
                    Method m1 = ((T) a).getClass().getMethod(method, null);
                    Method m2 = ((T) b).getClass().getMethod(method, null);
                    //倒序
                    if (sort != null && "desc".equals(sort)) {
                        ret = m2.invoke(((T) b), null).toString().compareTo(m1.invoke(((T) a), null).toString());
                    } else {
                        //正序
                        ret = m1.invoke(((T) a), null).toString().compareTo(m2.invoke(((T) b), null).toString());
                    }
                } catch (NoSuchMethodException ne) {
                    System.out.println(ne);
                } catch (IllegalAccessException ie) {
                    System.out.println(ie);
                } catch (InvocationTargetException it) {
                    System.out.println(it);
                }
                return ret;
            }
        });
    }
}
