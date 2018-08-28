package com.cdkj.order.dao;

import com.cdkj.common.base.model.dao.BaseMapper;
import com.cdkj.model.order.pojo.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface OrderMapper extends BaseMapper {

    /**
     * description: //TODO <br>
     *
     * @param sysAccount
     * @return java.lang.String
     */
    Order selectBySysAccount(@Param(value = "sysAccount") String sysAccount);
}