/**
 * project name:cdds
 * file name:ProductFeignHystric
 * package name:com.cdkj.feign.order.hystric
 * date:2018/6/29 17:12
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.feign.product.hystric;

import com.cdkj.feign.product.api.ProductFeignService;
import com.cdkj.util.JsonUtils;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * description: 产品接口异常处理 <br>
 * date: 2018/6/29 17:12
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@Component
public class ProductFeignHystric implements FallbackFactory<ProductFeignService> {
    @Override
    public ProductFeignService create(Throwable throwable) {
        return new ProductFeignService() {
            @Override
            public String selectOrderBySysAccount(String sysAccount) {
                throwable.printStackTrace();
                return JsonUtils.resFailed(throwable.getMessage());
            }

            @Override
            public String selectBySysAccount(String sysAccount) {
                throwable.printStackTrace();
                return JsonUtils.resFailed(throwable.getMessage());
            }
        };
    }
}