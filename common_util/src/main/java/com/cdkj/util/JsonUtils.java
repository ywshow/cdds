package com.cdkj.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.cdkj.common.constant.ResultCode;
import com.cdkj.common.exception.CustException;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utils - FastJsonUtils
 *
 * @author SHOP++ Team
 * @version 4.0
 */
public final class JsonUtils {

    /**
     * ObjectMapper
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static SerializerFeature[] FEATURES = new SerializerFeature[]{
            //输出key时是否使用双引号,默认为true
            //SerializerFeature.QuoteFieldNames,
            //是否输出值为null的字段,默认为false
            SerializerFeature.WriteMapNullValue,
            //数值字段如果为null,输出为0,而非null
            SerializerFeature.WriteNullNumberAsZero,
            //字符类型字段如果为null,输出为”“,而非null
            SerializerFeature.WriteNullStringAsEmpty,
            //List字段如果为null,输出为[],而非null
            SerializerFeature.WriteNullListAsEmpty,
            //Boolean字段如果为null,输出为false,而非null
            SerializerFeature.WriteNullBooleanAsFalse};
    protected static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    /**
     * 不可实例化
     */
    private JsonUtils() {
    }

    /**
     * 将对象转换为JSON字符串
     *
     * @param value 对象
     * @return JSON字符串
     */
    public static String toJson(Object value) {
        Assert.notNull(value, "Object is NULL");
        return JSON.toJSONString(value);
    }

    /**
     * 将对象转换为JSON字符串，并只包含includes的指定属性
     *
     * @param obj      要json化的对象
     * @param clazz    要过滤属性的对象的class
     * @param includes 要json的指定的属性数组。如果不需要，可以clazz和includes都传null
     * @return
     */
    public static String toJsonFilter(Object obj, Class<?> clazz, String[] includes) {
        Assert.notNull(obj, "Object is NULL");
        if (ValidateUtils.isNotEmptyArray(includes)) {
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(clazz, includes);
            return JSON.toJSONString(obj, filter, FEATURES);
        }
        return JSON.toJSONString(obj, FEATURES);
    }

    public static String resJsonFilter(Object obj, Class<?> clazz, String[] includes) {
        return toJsonFilter(ResultUtil.getResult(obj), clazz, includes);
    }

    public static String resJsonFilter(Object obj, Map<Class<?>, String[]> filterMap) {
        return toJsonFilter(ResultUtil.getResult(obj), filterMap);
    }

    /**
     * 将对象转换为JSON字符串，并只包含includes的指定属性
     *
     * @param obj       要json化的对象
     * @param filterMap key=clazz    要过滤属性的对象的class
     *                  value=includes 要json的指定的属性数组。如果不需要，可以clazz和includes都传null
     * @return
     */
    public static String toJsonFilter(Object obj, Map<Class<?>, String[]> filterMap) {
        Assert.notNull(obj, "Object is NULL");
        if (ValidateUtils.isNotEmptyMap(filterMap)) {
            MyPropertyPreFilter filter = new MyPropertyPreFilter(filterMap);
            return JSON.toJSONString(obj, filter, FEATURES);
        }
        return JSON.toJSONString(obj, FEATURES);
    }

    /**
     * 返回给前台的结果
     *
     * @param obj
     * @return
     */
    public static String res(Object obj) {
        return toJson(ResultUtil.getResult(obj));
    }

    /**
     * 返回给前台的结果{数据字段为data}
     *
     * @param obj
     * @return
     */
    public static String resData(Object obj) {
        return toJson(ResultUtil.getData(obj));
    }

    /**
     * 返回错误给前台的结果
     *
     * @return
     */
    public static String resFailed(String errorMsg) {
        return toJson(ResultUtil.getResult(ResultCode.FAIL, errorMsg));
    }

    /**
     * 返回给前台的结果
     *
     * @param count 更新数量
     * @return
     */
    public static String resCorU(int count) {
        if (count == 0) {
            return toJson(ResultUtil.getResult(ResultCode.FAIL));
        } else {
            return toJson(ResultUtil.getResult(ResultCode.SUCCESS));
        }
    }

    /**
     * 返回错误给前台的结果
     *
     * @return
     */
    public static String resFailed(int errorCode1, int errorCode2, String errorCode3, String errorMsg) {
        return toJson(ResultUtil.getResult(ResultCode.FAIL, errorCode1, errorCode2, errorCode3, errorMsg));
    }

    /**
     * 返回成功给前台的结果
     *
     * @return
     */
    public static String resSuccess() {
        return toJson(ResultUtil.getResult(ResultCode.SUCCESS));
    }

    /**
     * 将JSON字符串转换为对象
     *
     * @param json      JSON字符串
     * @param valueType 类型
     * @return 对象
     */
    public static <T> T toObject(String json, Class<T> valueType) {
        Assert.hasText(json, "Object has text");
        Assert.notNull(valueType, "Object is NULL");
        return JSON.parseObject(json, valueType);
    }

    /**
     * 将JSON字符串转换为对象
     *
     * @param json          JSON字符串
     * @param typeReference 类型
     * @return 对象
     */
    public static <T> T toObject(String json, TypeReference<?> typeReference) {
        Assert.hasText(json, "Object has text");
        Assert.notNull(typeReference, "Object is NULL");

        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (JsonParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 将JSON字符串转换为对象
     *
     * @param json     JSON字符串
     * @param javaType 类型
     * @return 对象
     */
    public static <T> T toObject(String json, JavaType javaType) {
        Assert.hasText(json, "Object has text");
        Assert.notNull(javaType, "Object is NULL");

        try {
            return OBJECT_MAPPER.readValue(json, javaType);
        } catch (JsonParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 将JSON字符串转换为树
     *
     * @param json JSON字符串
     * @return 树
     */
    public static JsonNode toTree(String json) {
        Assert.hasText(json, "Object has text");

        try {
            return OBJECT_MAPPER.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 将对象转换为JSON流
     *
     * @param writer Writer
     * @param value  对象
     */
    public static void writeValue(Writer writer, Object value) {
        Assert.notNull(writer, "writer not null");
        Assert.notNull(value, "object not null");

        try {
            OBJECT_MAPPER.writeValue(writer, value);
        } catch (JsonGenerationException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 构造类型
     *
     * @param type 类型
     * @return 类型
     */
    public static JavaType constructType(Type type) {
        Assert.notNull(type, "type not null");

        return TypeFactory.defaultInstance().constructType(type);
    }

    /**
     * 构造类型
     *
     * @param typeReference 类型
     * @return 类型
     */
    public static JavaType constructType(TypeReference<?> typeReference) {
        Assert.notNull(typeReference, "typeReference not null");

        return TypeFactory.defaultInstance().constructType(typeReference);
    }

    /**
     * FastJsonUtils 对象转换为List集合
     *
     * @param in
     * @param clsT
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> getObjectsFromJson(String in, Class<T> clsT) throws IOException {

        JSONArray ja = JSONArray.fromObject(in);

        List<T> list = new ArrayList<T>(ja.size());
        for (int i = 0; i < ja.size(); ++i) {
            T t = OBJECT_MAPPER.readValue(ja.getString(i), clsT);
            list.add(t);
        }
        return list;
    }

    /**
     * 根据JSON返回Map集合(单层JSON)
     *
     * @param json
     * @return
     */
    public static Map<String, Object> getMaptoFromJson(String json) {
        Map<String, Object> map = new HashMap<>();
        try {
            map = OBJECT_MAPPER.readValue(json, Map.class);
        } catch (IOException e) {
            logger.error("run error", e);
        }
        return map;
    }

    /**
     * 根据JSON返回Map集合(多层JSON)
     *
     * @param json
     * @return
     */
    public static List<Map<String, Object>> getMapsFromJson(String json) throws IOException {
        JSONArray ja = JSONArray.fromObject(json);

        List<Map<String, Object>> list = new ArrayList<>(ja.size());
        for (int i = 0; i < ja.size(); ++i) {
            Map<String, Object> t = OBJECT_MAPPER.readValue(ja.getString(i), Map.class);
            list.add(t);
        }

        return list;
    }

    /**
     * 根据MAP获取JSON,只能处理简单MAP
     *
     * @param map
     * @return
     */
    public static String getJsonFromMap(Map map) {
        JSONObject jsonObject = JSONObject.fromObject(map);
        return jsonObject.toString();
    }

    /**
     * 根据结果集获得对象
     *
     * @param json 对象json
     * @return
     */
    public static <T> T getObjectFromResultJson(String json, Class<T> clsT) {
        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(json);
        String key = "resultCode";
        //无数据，返回null
        if (jsonObject.getIntValue(key) == ResultCode.NO_DATA) {
            return null;
        }
        if (jsonObject.getIntValue(key) != ResultCode.SUCCESS) {
            throw new CustException(jsonObject.getString("errorMsg"));
        }
        return com.alibaba.fastjson.JSONObject.parseObject(jsonObject.getString("result"), clsT);
    }

    /**
     * 根据结果集获得对象
     *
     * @param json 对象json
     * @return
     */
    public static Object getObjectFromResultJson(String json) {
        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(json);
        String key = "resultCode";
        //无数据，返回null
        if (jsonObject.getIntValue(key) == ResultCode.NO_DATA) {
            return null;
        }
        if (jsonObject.getIntValue(key) != ResultCode.SUCCESS) {
            throw new CustException(jsonObject.getString("errorMsg"));
        }
        return jsonObject.getString("result");
    }

    /**
     * 根据结果集获得数组对象
     *
     * @param json 对象json
     * @return
     * @Author wq
     */
    public static <T> List<T> getListObjectFromResultJson(String json, Class<T> clsT) {
        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(json);
        String key = "resultCode";
        //无数据，返回null
        if (jsonObject.getIntValue(key) == ResultCode.NO_DATA) {
            return null;
        }
        if (jsonObject.getIntValue(key) != ResultCode.SUCCESS) {
            throw new CustException(jsonObject.getString("errorMsg"));
        }
        return com.alibaba.fastjson.JSONArray.parseArray(jsonObject.getString("result"), clsT);
    }
}