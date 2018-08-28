package com.cdkj.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * PackageName:com.cdkj.common.util
 * Descript:时间ID生成器 <br/>
 * date: 2016/3/31 <br/>
 * User: jyune
 * version 1.0
 */
public class SmsIdUtil {
    public static Long getDateTimeId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
        return Long.valueOf(sdf.format(new Date()));
    }
}
