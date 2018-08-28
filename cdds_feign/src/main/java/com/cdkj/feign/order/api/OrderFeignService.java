/**
 * project name:cdds
 * file name:OrderFeignService
 * package name:com.cdkj.feign.order.api
 * date:2018/6/28 17:28
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.feign.order.api;

import com.cdkj.feign.order.hystric.OrderFeignHystric;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * description: 订单接口 <br>
 * date: 2018/6/28 17:28
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@FeignClient(name = "service-order", fallback = OrderFeignHystric.class)
public interface OrderFeignService {

    /**
     * description: //TODO <br>
     *
     * @param sysAccount
     * @return java.lang.String
     */
    @GetMapping(value = "/cdds-order/order/selectBySysAccount/{sysAccount}")
    String selectBySysAccount(@PathVariable("sysAccount") String sysAccount);
}