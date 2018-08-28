//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cdkj.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

public class ObjUtil {
    protected static Logger logger = LoggerFactory.getLogger(ObjUtil.class);

    public ObjUtil() {
    }

    public static String getDate(Date d, String format) {
        return null != d ? DateUtil.format(d, format) : "";
    }

    public static Date getDate(String d, String format) {
        return null != d && !d.equals("") ? DateUtil.format(d, format) : null;
    }

    public static String isNull(Object obj, String... methodNameArr) {
        StringBuffer sb = new StringBuffer();
        Class c = obj.getClass();
        Field[] sfieldList = c.getDeclaredFields();
        Field[] pFieldList = c.getSuperclass().getDeclaredFields();
        Field[] fieldList = new Field[sfieldList.length + pFieldList.length];
        System.arraycopy(sfieldList, 0, fieldList, 0, sfieldList.length);
        System.arraycopy(pFieldList, 0, fieldList, sfieldList.length, pFieldList.length);

        for (int retStr = 0; retStr < methodNameArr.length; ++retStr) {
            String methodName = methodNameArr[retStr];
            boolean _t = isNull(obj, fieldList, methodName);
            if (_t) {
                sb.append(methodName);
                sb.append(",");
            }
        }

        String var10 = sb.toString().trim();
        if (!var10.equals("")) {
            return var10.substring(0, var10.length() - 1);
        } else {
            return null;
        }
    }

    private static boolean isNull(Object obj, Field[] fieldList, String methodName) {
        try {
            for (int e = 0; e < fieldList.length; ++e) {
                Field field = fieldList[e];
                field.setAccessible(true);
                if (field.getName().equals(methodName)) {
                    if (field.getGenericType().toString().indexOf("String") != -1) {
                        if (field.get(obj) == null) {
                            return true;
                        }

                        return isNull(field.get(obj).toString());
                    }

                    return isNull(field.get(obj));
                }
            }

            return true;
        } catch (Exception var5) {
            logger.error("run error", var5);
            return true;
        }
    }

    private static boolean isNull(String obj) {
        return obj == null || obj.trim().equals("");
    }

    private static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isEmptyList(List obj) {
        return null == obj || obj.size() <= 0;
    }

    /**
     * 把OBJ1转换成OBJ2 只转换相同字段名的属性
     *
     * @param obj1
     * @param obj2
     */
    public static void obj1ToObj2(Object obj1, Object obj2) throws Exception {
        Class obj1Cls = obj1.getClass();
        Class obj2Cls = obj2.getClass();
        Field[] fields1 = obj1Cls.getDeclaredFields();
        Field[] fields2 = obj2Cls.getDeclaredFields();

        String fieldName1;
        String methodName1;
        Method invoke1;
//        String value1;

        String fieldName2;
//        String methodName2;
//        Method invoke2;
//        String value2;
        for (int i = 0; i < fields2.length; i++) {
            fieldName2 = fields2[i].getName();
            fields2[i].setAccessible(true);
            for (int j = 0; j < fields1.length; j++) {
                fieldName1 = fields1[j].getName();
                if (fieldName2.equals(fieldName1)) {
                    methodName1 = "get" + Character.toUpperCase(fieldName1.charAt(0)) + fieldName1.substring(1);
                    invoke1 = obj1Cls.getMethod(methodName1);

                    fields2[i].set(obj2, invoke1.invoke(obj1));
                    break;
                }
            }
        }


    }
}
