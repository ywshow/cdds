/**
 * project name:cdds
 * file name:OrderFeignHystric
 * package name:com.cdkj.feign.order.hystric
 * date:2018/6/28 17:31
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.feign.order.hystric;

import com.cdkj.feign.order.api.OrderFeignService;
import com.cdkj.util.JsonUtils;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * description:订单接口异常处理 <br>
 * date: 2018/6/28 17:31
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@Component
public class OrderFeignHystric implements FallbackFactory<OrderFeignService> {

    private static final Logger logger = LoggerFactory.getLogger(OrderFeignHystric.class);

    @Override
    public OrderFeignService create(Throwable throwable) {
        return new OrderFeignService() {
            @Override
            public String selectBySysAccount(String sysAccount) {
                logger.error("OrderFeignHystric.selectBySysAccount.error:{}", throwable);
                return JsonUtils.resFailed("账套号查询失败!");
            }
        };
    }
}