package com.cdkj.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PackageName:com.cdkj.common.util
 * Descript:距离计算 <br/>
 * date: 2016/4/1 <br/>
 * User: jyune
 * version 1.0
 */
public class GeoUtils {

    protected static Logger logger = LoggerFactory.getLogger(GeoUtils.class);

    /**
     * 计算两经纬度点之间的距离（单位：米）
     *
     * @param lng1 经度
     * @param lat1 纬度
     * @param lng2
     * @param lat2
     * @return
     */
    public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = Math.toRadians(lat1);
        double radLat2 = Math.toRadians(lat2);
        double a = radLat1 - radLat2;
        double b = Math.toRadians(lng1) - Math.toRadians(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
                * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        // 取WGS84标准参考椭球中的地球长半径(单位:m)
        s = s * 6378137.0;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    public static void main(String[] args) {

        logger.debug(String.valueOf(getDistance(112.870224, 28.231694, 112.873371, 28.227168)));
//       logger.debug(getDistance(121.446014,31.215937,121.446028464238,31.2158502442799));
    }
}
