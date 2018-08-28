/**
 * project name:cdds
 * file name:ProductController
 * package name:com.cdkj.product
 * date:2018/6/25 17:23
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.product.controller;

import com.cdkj.common.base.controller.BaseController;
import com.cdkj.common.exception.CustException;
import com.cdkj.feign.order.api.OrderFeignService;
import com.cdkj.model.product.pojo.Product;
import com.cdkj.product.service.api.ProductService;
import com.cdkj.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 产品管理 <br>
 * date: 2018/6/25 17:23
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/product/")
public class ProductController extends BaseController<Product> {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderFeignService orderFeignService;

    @GetMapping(value = "test")
    public String test() {
        try {
            List list = new ArrayList();
        } catch (Exception e) {
            logger.error("系统异常", e);
            e.printStackTrace();
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>)))))))))))))))))");
        return "hello";
    }

    @GetMapping(value = "selectBySysAccount/{sysAccount}")
    public String selectBySysAccount(@PathVariable("sysAccount") String sysAccount) {
        try {
            Product product = productService.selectBySysAccount(sysAccount);
            return JsonUtils.res(product);
        } catch (CustException e) {
            logger.error("ProductController.selectBySysAccount.error:",e);
            return JsonUtils.resFailed("异常1");
        } catch (Exception e) {;
            logger.error("ProductController.selectBySysAccount.error:",e);
            return JsonUtils.resFailed("异常");
        }
    }


    @GetMapping("selectOrderBySysAccount/{sysAccount}")
    public String selectOrderBySysAccount(@PathVariable(value = "sysAccount") String sysAccount) {
        try {
            String result = orderFeignService.selectBySysAccount(sysAccount);
            return result;
        } catch (CustException e) {
            logger.error("ProductController.selectOrderBySysAccount.error:",e);
            return JsonUtils.resFailed("异常002");
        } catch (Exception e) {
            logger.error("ProductController.selectOrderBySysAccount.error",e);
            return JsonUtils.resFailed("异常001");
        }
    }
}