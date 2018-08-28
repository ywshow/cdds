/**
 * project name:ach
 * file name:TldIdentifyCodeService
 * package name:com.cdkj.system.service.api
 * date:2017-08-25 14:04
 * author:yw
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.msg.service.api;



import com.cdkj.common.base.service.api.BaseService;
import com.cdkj.model.msg.pojo.MsgIdentifyCode;

import java.util.Map;

/**
 * description: 用户验证码管理服务层
 * date: 2017-08-25 14:04
 *
 * @author yw
 * @version 1.0
 * @since JDK 1.8
 */
public interface MsgIdentifyCodeService extends BaseService<MsgIdentifyCode, String> {
    /**
     * 发送验证码
     *
     * @param mobile 手机号码
     * @return
     */
    Map<String, Object> send(String sysAccount, String mobile);

    /**
     * 验证验证码
     *
     * @param mobile 手机号码
     * @param code   验证码
     * @return
     */
    boolean checkIdentifyCode(String sysAccount, String mobile, String code);
}
