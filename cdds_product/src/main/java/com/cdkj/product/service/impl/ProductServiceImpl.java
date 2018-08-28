/**
 * project name:cdds
 * file name:ProductServiceImpl
 * package name:com.cdkj.product.service.impl
 * date:2018/6/29 11:48
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.product.service.impl;

import com.cdkj.common.base.service.impl.BaseServiceImpl;
import com.cdkj.common.exception.CustException;
import com.cdkj.constant.ErrorCode;
import com.cdkj.model.product.pojo.Product;
import com.cdkj.product.dao.ProductMapper;
import com.cdkj.product.service.api.ProductService;
import com.cdkj.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: 产品信息 <br>
 * date: 2018/6/29 11:48
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, String> implements ProductService {

    @Autowired
    private ProductMapper productMapper;


    /**
     * description: 账套号查询 <br>
     *
     * @param sysAccount 账套号
     * @return Product
     */
    @Override
    public Product selectBySysAccount(String sysAccount) {
        if(StringUtil.isEmpty(sysAccount)){
            throw new CustException(ErrorCode.ERROR_20001,"套账号为空");
        }
        return productMapper.selectBySysAccount(sysAccount);
    }
}