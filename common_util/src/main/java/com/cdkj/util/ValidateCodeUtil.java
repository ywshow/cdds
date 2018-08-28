package com.cdkj.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sky on 2016/2/25.
 */
public class ValidateCodeUtil {


    public static Map<String, Object> getResult(String str) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", str);
        return map;
    }

    public static Map<String, Object> getCode() {
        String code = "";
        code += (int) (Math.random() * 9 + 1);
        for (int i = 0; i < 5; i++) {
            code += (int) (Math.random() * 10);
        }
        return getResult(code);
    }

}
