/**
 * project name:cdds
 * file name:TestService
 * package name:com.cdkj.schedule.schedule.service.api
 * date:2018/9/5 17:03
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.schedule.schedule.service.api;

import com.cdkj.model.schedule.pojo.Schedule;

/**
 * description: //TODO <br>
 * date: 2018/9/5 17:03
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
public interface TestService {

    void test();

    int save(Schedule schedule);

    void info();
}