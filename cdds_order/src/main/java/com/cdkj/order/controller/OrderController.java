/**
 * project name:cdds
 * file name:OrderController
 * package name:com.cdkj.feign.order.controller
 * date:2018/6/29 10:21
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.order.controller;

import com.cdkj.common.base.controller.BaseController;
import com.cdkj.common.exception.CustException;
import com.cdkj.util.JsonUtils;
import com.cdkj.feign.product.api.ProductFeignService;
import com.cdkj.order.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description: //TODO <br>
 * date: 2018/6/29 10:21
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */

@RestController
@RequestMapping(value = "/order/")
public class OrderController extends BaseController<String> {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductFeignService productFeignService;

    @GetMapping(value = "selectById/{id}")
    public String selectById(@PathVariable("id") String id) {
        try {
            return JsonUtils.res(orderService.selectById(id));
        } catch (CustException e) {
            return JsonUtils.resFailed("异常");
        } catch (Exception e) {
            return JsonUtils.resFailed("1");
        }
    }


    @GetMapping(value = "selectBySysAccount/{sysAccount}")
    public String selectBySysAccount(@PathVariable("sysAccount") String sysAccount) {
        try {
//            String result = productFeignService.selectOrderBySysAccount(sysAccount);
            logger.error("订单端返回》》》》》》》》》》》》》》》》》》》》》》》》》》》》");
            return JsonUtils.res("订单端返回");
        } catch (CustException e) {
            return JsonUtils.resFailed("异常");
        } catch (Exception e) {
            logger.error("selectBySysAccount.Exception.error:{}",e);
            return JsonUtils.resFailed("5555555555555555555");
        }
    }

    @GetMapping(value = "selectProductBySysAccount/{sysAccount}")
    public String selectProductBySysAccount(@PathVariable(value = "sysAccount") String sysAccount) {
        try {
            String result = productFeignService.selectBySysAccount(sysAccount);
            return result;
        } catch (CustException e) {
            return JsonUtils.resFailed("异常");
        } catch (Exception e) {
            logger.error("selectBySysAccount.Exception.error:{}",e);
            return JsonUtils.resFailed("sssssssssssss");
        }
    }
}