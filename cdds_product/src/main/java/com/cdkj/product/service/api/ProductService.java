/**
 * project name:cdds
 * file name:ProductService
 * package name:com.cdkj.product.service.api
 * date:2018/6/29 11:48
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.product.service.api;

import com.cdkj.common.base.service.api.BaseService;
import com.cdkj.model.product.pojo.Product;

/**
 * description: 产品信息 <br>
 * date: 2018/6/29 11:48
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
public interface ProductService extends BaseService<Product,String> {

    /**
     * description: 账套号查询 <br>
     *
     * @param sysAccount 账套号
     * @return Product
     */
    Product selectBySysAccount(String sysAccount);
}