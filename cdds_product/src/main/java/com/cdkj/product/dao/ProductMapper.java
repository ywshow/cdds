package com.cdkj.product.dao;

import com.cdkj.common.base.model.dao.BaseMapper;
import com.cdkj.model.product.pojo.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface ProductMapper extends BaseMapper {

    /**
     * description: 账套号查询 <br>
     *
     * @param sysAccount 账套号
     * @return Product
     */
    Product selectBySysAccount(@Param(value = "sysAccount") String sysAccount);
}