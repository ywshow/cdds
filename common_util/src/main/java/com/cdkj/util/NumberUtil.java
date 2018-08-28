/**
 * project name:sdp-base
 * file name:NumberUtils
 * package name:com.cdkj.common.util
 * date:2017-03-17 13:21
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.util;

import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

/**
 * description: //用于空值减法控制负数 <br>
 * date: 2017-03-17 13:21
 *
 * @author jyune
 * @version 1.0
 * @since JDK 1.8
 */
public class NumberUtil {

    public static int INT_ZERO = 0;
    public static Long LONG_ZERO = 0L;
    public static int INT_ONE = 1;
    public static Long LONG_ONE = 1L;

    /**
     * int处理，为了防止减出负数而处理，如果减数为空，返回0；如果小于0 则返回0
     *
     * @param old    减数
     * @param change 被减数
     * @return 差
     */
    public static int subtract(int old, int change) {
        if (ObjectUtils.isEmpty(old) && ObjectUtils.isEmpty(change)) {
            return INT_ZERO;
        }
        if (ObjectUtils.isEmpty(old)) {
            return INT_ZERO;
        }
        if (ObjectUtils.isEmpty(change)) {
            return old;
        }
        if (old - change < 0) {
            return INT_ZERO;
        }
        return old - change;
    }

    /**
     * Long处理，为了防止减出负数而处理，如果减数为空，返回0；如果小于0 则返回0
     *
     * @param old    减数
     * @param change 被减数
     * @return 差
     */
    public static Long subtract(Long old, Long change) {
        if (ObjectUtils.isEmpty(old) && ObjectUtils.isEmpty(change)) {
            return LONG_ZERO;
        }
        if (ObjectUtils.isEmpty(old)) {
            return LONG_ZERO;
        }
        if (ObjectUtils.isEmpty(change)) {
            return old;
        }
        if (old - change < 0) {
            return LONG_ZERO;
        }
        return old - change;
    }

    /**
     * BigDecimal处理，为了防止减出负数而处理，如果减数为空，返回0；如果小于0 则返回0
     *
     * @param old    减数
     * @param change 被减数
     * @return 差
     */
    public static BigDecimal subtract(BigDecimal old, BigDecimal change) {
        if (ObjectUtils.isEmpty(old) && ObjectUtils.isEmpty(change)) {
            return BigDecimal.ZERO;
        }
        if (ObjectUtils.isEmpty(old)) {
            return BigDecimal.ZERO;
        }
        if (ObjectUtils.isEmpty(change)) {
            return old;
        }
        if (old.subtract(change).compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }
        return old.subtract(change);
    }
}
