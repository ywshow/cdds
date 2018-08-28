package com.cdkj.common.base.service.api;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T, O extends Serializable> {
    /**
     * 根据主键删除对象 T 类型,O 主键类型
     * @param id 主键
     * @return 成功记录数
     */
    int deleteByPrimaryKey(O id);

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
     * @param id 主键
     * @return 对象
     */
    T selectByPrimaryKey(O id);

    /**
     * 更新对象 空值列不会更新
     * @param record 对象
     * @return 变更记录数
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * 更新对象,并判断乐观锁是否生效,如果生效,抛出RUNTIME_EXCEPTION
     *
     * @param record 更新对象
     * @return
     */
    int updateByPrimaryKeySelectiveWithKey(T record);

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