package com.cdkj.common.base.service.impl;

import com.cdkj.common.base.model.dao.BaseMapper;
import com.cdkj.common.base.service.api.BaseService;
import com.cdkj.common.constant.Constant;
import com.cdkj.common.exception.CustException;
import com.cdkj.common.redis.RedisClient;
import com.cdkj.util.DateUtil;
import com.cdkj.util.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service - 基类
 *
 * @author bovine
 * @version 3.0
 */
@Transactional(rollbackFor = CustException.class)
public class BaseServiceImpl<T, O extends Serializable> implements BaseService<T, O> {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BaseMapper<T, O> baseMapper;

    @Autowired
    protected RedisClient redisClient;

    @Override
    public int deleteByPrimaryKey(O id) {
        return baseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(T record) {
        return baseMapper.insert(record);
    }

    @Override
    public int insertSelective(T record) {
        return baseMapper.insertSelective(record);
    }

    @Override
    public T selectByPrimaryKey(O id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(T record) {
        return baseMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKeySelectiveWithKey(T record) {
        int updateTag = baseMapper.updateByPrimaryKeySelective(record);
        if (updateTag == 0) {
            throw new CustException(100001, this.getClass() + "updateByPrimaryKeySelectiveWithKey-乐观锁生效,为防止脏数据,变更佣金取消,回滚相关操作!");
        }
        return updateTag;
    }

    @Override
    public int updateByPrimaryKey(T record) {
        return baseMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<T> listByPrimaryKeySelective(T record) {
        return baseMapper.listByPrimaryKeySelective(record);
    }

    /**
     * description: 初始化默认字段信息 <br>
     *
     * @param userId 登录用户ID
     * @param model  对象信息
     * @return 返回对应实体对象
     */
    protected <T> T initDefaultPrpperty(String userId, T model) {
        try {
            Class cls = model.getClass().getSuperclass();
            Field[] fields = cls.getDeclaredFields();
            Map<String, String> tmpMap = Arrays.stream(fields).collect(Collectors.toMap(Field::getName, Field::getName));

            invokeSetValue("id", model, cls, tmpMap, UUIDGenerator.randomUUID());

            invokeSetValue("createBy", model, cls, tmpMap, userId);

            invokeSetValue("createDt", model, cls, tmpMap, DateUtil.getNow());

            invokeSetValue("updateDt", model, cls, tmpMap, DateUtil.getNow());

            invokeSetValue("updateBy", model, cls, tmpMap, userId);

            invokeSetValue("version", model, cls, tmpMap, Constant.VERSION_DEFAULT);

            invokeSetValue("status", model, cls, tmpMap, Constant.STATUS_DEFAULT);

            invokeSetValue("enabled", model, cls, tmpMap, Constant.ENABLED_Y);

            return model;
        } catch (Exception e) {
            logger.error("BaseServiceImpl.initDefaultPrpperty异常", e);
            throw new CustException("初始化异常");
        }
    }

    /**
     * description: 赋值 <br>
     *
     * @param param  key（字段名）
     * @param model  对象信息
     * @param cls    class
     * @param tmpMap 字段map
     * @param value  值
     * @return void
     */
    private void invokeSetValue(String param, Object model, Class cls, Map<String, String> tmpMap, Object value) throws Exception {
        String property = tmpMap.get(param);
        property = property.substring(0, 1).toUpperCase() + property.substring(1);
        Method methodPropertySet = cls.getMethod("set" + property, value.getClass());
        methodPropertySet.invoke(model, value);
    }
}