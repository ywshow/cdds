package com.cdkj.util;

/**
 * Created by bovine on 2016/6/22.
 */

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.*;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class PojoUtils {

    static {
        ConvertUtils.register(new LongConverter(null), Long.class);
        ConvertUtils.register(new ByteConverter(null), Byte.class);
        ConvertUtils.register(new IntegerConverter(null), Integer.class);
        ConvertUtils.register(new DoubleConverter(null), Double.class);
        ConvertUtils.register(new ShortConverter(null), Short.class);
        ConvertUtils.register(new FloatConverter(null), Float.class);
    }

    /**
     * 将对象转换为HashMap
     *
     * @param obj
     * @return
     */
    public static Map<String, String> objectToHash(Object obj) {
        try {
            Map<String, String> map = new HashMap<>();
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                if (!property.getName().equals("class")) {
                    if (property.getReadMethod().invoke(obj) != null) {
                        map.put(property.getName(), "" + property.getReadMethod().invoke(obj));
                    }
                }
            }
            return map;
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}