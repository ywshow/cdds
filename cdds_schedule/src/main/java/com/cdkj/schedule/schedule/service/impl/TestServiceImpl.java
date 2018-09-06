/**
 * project name:cdds
 * file name:TestServiceImpl
 * package name:com.cdkj.schedule.schedule.service.impl
 * date:2018/9/5 17:03
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.schedule.schedule.service.impl;


import com.cdkj.common.base.service.impl.BaseServiceImpl;
import com.cdkj.model.schedule.pojo.Schedule;
import com.cdkj.schedule.schedule.service.api.TestService;
import com.cdkj.util.JsonUtils;
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
public class TestServiceImpl extends BaseServiceImpl<Schedule,String> implements TestService {
    @Override
    public void test() {
        logger.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Override
    public int save(Schedule schedule) {
        logger.error("000000000000000000000000000000000000000000"+ JsonUtils.res(schedule));
        return 0;
    }

    @Override
    public void info() {
        logger.error("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
    }
}