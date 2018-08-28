/**
 * project name:cdds
 * file name:OrderService
 * package name:com.cdkj.feign.order.service.api
 * date:2018/6/29 11:13
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.order.service.api;

import com.cdkj.common.base.service.api.BaseService;
import com.cdkj.model.order.pojo.Order;

/**
 * description: 订单接口 <br>
 * date: 2018/6/29 11:13
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
public interface OrderService extends BaseService<Order, String> {

    /**
     * description: 根据ID查询 <br>
     *
     * @param id ID查询
     * @return json
     */
    Order selectById(String id);

    /**
     * description: //TODO <br>
     *
     * @param sysAccount
     * @return java.lang.String
     */
    Order selectBySysAccount(String sysAccount);
}