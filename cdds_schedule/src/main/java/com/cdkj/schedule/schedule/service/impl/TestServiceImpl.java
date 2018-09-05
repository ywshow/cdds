/**
 * project name:cdds
 * file name:TestServiceImpl
 * package name:com.cdkj.schedule.schedule.service.impl
 * date:2018/9/5 17:03
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.schedule.schedule.service.impl;


import com.cdkj.schedule.schedule.service.api.TestService;
import org.springframework.stereotype.Service;

/**
 * description: //TODO <br>
 * date: 2018/9/5 17:03
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public class TestServiceImpl implements TestService {
    @Override
    public void test() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}