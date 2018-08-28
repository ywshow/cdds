package com.cdkj.common.base.model.dao;

import java.io.Serializable;
import java.util.List;

/**
 * PackageName:com.cdkj.base
 * Descript:dao基类，抽象CRUD<br/>
 * date: 2016-2-19 <br/>
 * User: bovine
 * version 1.0
 */
public interface BaseMapper<T, O extends Serializable> {
    /**
     * 根据主键删除对象 T 类型,O 主键类型
     * @param key 主键
     * @return 成功记录数
     */
    int deleteByPrimaryKey(O key);

    /**
     * 插入对象 空值字段将会插入空
     * @param record 被插入对象
     * @return 变更记录数
     */
    int insert(T record);

    /**
     * 插入对象 空值字段则不会插入
     * @param record 被插入对象
     * @return 变更记录数
     */
    int insertSelective(T record);

    /**
     * 根据主键查询对象
     * @param key 主键d
     * @return 对象
     */
    T selectByPrimaryKey(O key);

    /**
     * 更新对象 空值列不会更新
     * @param record 对象
     * @return 变更记录数
     */
    int updateByPrimaryKeySelective(T record);
    /**
     * 更新对象 空值列也会更新
     * @param record 对象
     * @return 变更记录数
     */
    int updateByPrimaryKey(T record);

    /**
     * 根据有值对象查询列表
     *
     * @param record 除开 remark,memo,create_dt,create_by,update_dt
     * @return 资产列表信息
     */
    List<T> listByPrimaryKeySelective(T record);
}