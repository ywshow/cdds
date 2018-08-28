package com.cdkj.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * description: //TODO <br>
 * date: 2017/12/6 16:54
 *
 * @author Administrator
 * @version 1.0
 * @since JDK 1.8
 */
public class Request2Map {

    /**
     * 请求参数转map
     * @param request
     * @return
     */
    public static Map<String,String> creatParamMap(HttpServletRequest request) {
        Map<String,String> map = new HashMap<String,String>();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();

            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }

        return map;
    }
}
