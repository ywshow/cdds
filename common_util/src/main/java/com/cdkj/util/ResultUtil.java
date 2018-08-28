package com.cdkj.util;

import com.cdkj.common.constant.ResultCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 名称：ResultUtil.java<br>
 * 描述: 跟页面交互的结果工具类<br>
 * 类型: JAVA<br>
 * 最近修改时间:2013-3-13
 *
 * @author twf
 */
public class ResultUtil {

    /**
     * 方法名 getResult
     * 方法描述
     * 返回结果
     *
     * @return String 结果是map类型的
     * resultCode : 0 代表访问数据库返回 null
     * 1   代表正常
     * 2   如果是列表时代表访问数据库正常，但是没有数据被查询出来
     * result ：如果是查询时，查询的列表
     * @变更记录 2013-3-13 下午3:06:38   twf  创建
     */
    public static Map<String, Object> getResult(List<?> list, PageDTO page) {
        Map<String, Object> map = new HashMap<>();
        int resultCode = ResultCode.FAIL;
        if (list == null) {
            resultCode = ResultCode.FAIL;
        } else if (list.size() > 0) {
            resultCode = ResultCode.SUCCESS;
        } else {
            resultCode = ResultCode.NO_DATA;
        }
        map.put("resultCode", resultCode);
        map.put("totalNum", page.getTotal());
        map.put("pageNo", page.getPageNumber());
        map.put("pageSise", page.getPageSize());
        map.put("result", list);
        return map;
    }

    public static Map<String, Object> getResult(int resultCode, String errorMsg) {
        Map<String, Object> map = new HashMap<>();
        map.put("resultCode", resultCode);
        map.put("errorMsg", errorMsg);
        return map;
    }


    public static Map<String, Object> getResult(int resultCode, String msg, String errorMsg) {
        Map<String, Object> map = new HashMap<>();
        map.put("resultCode", resultCode);
        map.put("message", msg);
        map.put("errorMsg", errorMsg);
        return map;
    }

    public static Map<String, Object> getResult(int resultCode, int errorCode1, int errorCode2, String errorCode3, String errorMsg) {
        Map<String, Object> map = new HashMap<>();
        map.put("resultCode", resultCode);
        map.put("errorMsg", errorCode1 + "-" + errorCode2 + "-" + errorCode3 + ":" + errorMsg);
        return map;
    }

    public static Map<String, Object> getResult(int resultCode) {
        Map<String, Object> map = new HashMap<>();
        map.put("resultCode", resultCode);
        return map;
    }

    public static Map<String, Object> getResult(boolean resultCode) {
        Map<String, Object> map = new HashMap<>();
        map.put("resultCode", (resultCode ? ResultCode.SUCCESS : ResultCode.FAIL));
        return map;
    }

    public static Map<String, Object> getResult(Object obj) {
        Map<String, Object> map = new HashMap<>(5);
        if((obj == null)){
            map.put("resultCode", ResultCode.NO_DATA);
            map.put("errorMsg", "无相关数据");
        }else{
            map.put("resultCode", ResultCode.SUCCESS);
        }
        map.put("result", obj);

        return map;
    }

    public static Map<String, Object> getData(Object obj) {
        Map<String, Object> map = new HashMap<>(5);
        map.put("resultCode", ((obj == null) ? ResultCode.FAIL : ResultCode.SUCCESS));
        map.put("data", obj);
        return map;
    }

    public static Map<String, Object> getResult(List<?> list, Map<String, Object> argMap) {
        Map<String, Object> map = initResult(list);
        map.put("map", argMap);
        return map;
    }

    public static Map<String, Object> getResult(List<?> list) {
        return initResult(list);
    }

    public static Map<String, Object> initResult(List<?> list) {
        Map<String, Object> map = new HashMap<>();
        int resultCode;
        if (list == null) {
            resultCode = ResultCode.FAIL;
        } else if (list.size() > 0) {
            resultCode = ResultCode.SUCCESS;
        } else {
            resultCode = ResultCode.NO_DATA;
        }
        map.put("resultCode", resultCode);
        map.put("result", list);
        return map;
    }

    public static Map<String, Object> getResult(int resultCode, String str, int type) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("resultCode", resultCode);
        switch (type) {
            case 1:
                map.put("errorMsg", str);
                break;
            case 2:
                map.put("result", str);
                break;
            default:
                map.put("msg", str);
        }
        return map;
    }
}
