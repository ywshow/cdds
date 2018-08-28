package com.cdkj.util;

import java.util.Collection;
import java.util.Map;

/**
 * Created by liuhaiyin on 2016/4/28.
 */
public class ValidateUtils {
    public static boolean equals(String str1, String str2) {
        return null == str1 ? false : str1.equals(str2);
    }

    public static boolean isEmptyCollection(Collection co) {
        return null == co || co.size() <= 0;
    }

    public static boolean isNotEmptyCollection(Collection co) {
        return !isEmptyCollection(co);
    }

    public static boolean isEmptyMap(Map map) {
        if (null == map || map.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmptyMap(Map map) {
        return !isEmptyMap(map);
    }

    public static boolean isEmptyArray(Object[] objects) {
        if (null == objects || objects.length < 1) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmptyArray(Object[] objects) {
        return !isEmptyArray(objects);
    }
}
