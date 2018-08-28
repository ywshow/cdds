/**
 * project name:cdds
 * file name:OrderServiceImpl
 * package name:com.cdkj.feign.order.service.impl
 * date:2018/6/29 11:14
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.order.service.impl;

import com.cdkj.common.base.service.impl.BaseServiceImpl;
import com.cdkj.common.exception.CustException;
import com.cdkj.util.StringUtil;
import com.cdkj.constant.ErrorCode;
import com.cdkj.model.order.pojo.Order;
import com.cdkj.order.dao.OrderMapper;
import com.cdkj.order.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: 订单实现 <br>
 * date: 2018/6/29 11:14
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, String> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * description: 根据ID查询 <br>
     *
     * @param id ID查询
     * @return json
     */
    @Override
    public Order selectById(String id) {
        if (StringUtil.isEmpty(id)) {
            throw new CustException(ErrorCode.ERROR_20001, "ID为空");
        }
        return (Order) orderMapper.selectByPrimaryKey(id);
    }

    /**
     * description: //TODO <br>
     *
     * @param sysAccount
     * @return java.lang.String
     */
    @Override
    public Order selectBySysAccount(String sysAccount) {
        if (StringUtil.isEmpty(sysAccount)) {
            throw new CustException(ErrorCode.ERROR_20001, "套账号为空");
        }
        return orderMapper.selectBySysAccount(sysAccount);
    }
}