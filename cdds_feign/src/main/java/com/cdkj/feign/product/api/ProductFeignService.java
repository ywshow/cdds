/**
 * project name:cdds
 * file name:ProductFeignService
 * package name:com.cdkj.feign.order.api
 * date:2018/6/29 17:11
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.feign.product.api;

import com.cdkj.feign.product.hystric.ProductFeignHystric;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * description: 产品接口 <br>
 * date: 2018/6/29 17:11
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@FeignClient(name = "service-product", fallback = ProductFeignHystric.class)
public interface ProductFeignService {

    @GetMapping("/cdds-product/product/selectOrderBySysAccount/{sysAccount}")
    String selectOrderBySysAccount(@PathVariable(value = "sysAccount") String sysAccount);

    @GetMapping("/cdds-product/product/selectBySysAccount/{sysAccount}")
    String selectBySysAccount(@PathVariable(value = "sysAccount") String sysAccount);
}