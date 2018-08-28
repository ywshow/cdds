/**
 * project name:ach
 * file name:SmsService
 * package name:com.cdkj.system.service.api
 * date:2017-08-25 16:30
 * author:yw
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.msg.service.api;


import java.util.Map;

/**
 * description: //TODO <br>
 * date: 2017-08-25 16:30
 *
 * @author yw
 * @version 1.0
 * @since JDK 1.8
 */
public interface SmsService {
    /**
     * 推送短信接口
     *
     * @param mobile 推送手机号码
     * @param msg    推送内容
     * @return 推送结果
     */
    Map<String, String> send(String sysAccount, String mobile, String msg);

}
