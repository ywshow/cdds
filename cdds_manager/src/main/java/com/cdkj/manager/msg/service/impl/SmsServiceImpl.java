/**
 * project name:ach
 * file name:SmsServiceImpl
 * package name:com.cdkj.system.service.impl
 * date:2017-08-25 16:31
 * author:yw
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.msg.service.impl;


import com.cdkj.util.JsonUtils;
import com.cdkj.constant.SysDictConstants;
import com.cdkj.manager.msg.dao.MsgIdentifyCodeMapper;
import com.cdkj.manager.msg.service.api.SmsService;
import com.cdkj.manager.system.dao.SysDictMapper;
import com.cdkj.util.sms.SmsApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * description: 短信推送业务
 * date: 2017-08-25 16:31
 *
 * @author yw
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public class SmsServiceImpl implements SmsService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SysDictMapper sysDictMapper;
    @Resource
    private MsgIdentifyCodeMapper msgIdentifyCodeMapper;

    /**
     * 推送短信接口
     *
     * @param mobile 推送手机号码
     * @param msg    推送内容
     * @return 推送结果
     */
    @Override
    public Map<String, String> send(String sysAccount, String mobile, String msg) {
        //接口地址
        String url = sysDictMapper.selectByGroupCode(sysAccount, SysDictConstants.GROUP_CODE.MSG_TYPE, SysDictConstants.MSG_TYPE.SMS_INTERFACE_ADDR);
        //账号
        String account = sysDictMapper.selectByGroupCode(sysAccount, SysDictConstants.GROUP_CODE.MSG_TYPE, SysDictConstants.MSG_TYPE.SMS_ACCOUNT);
        //接口密码
        String password = sysDictMapper.selectByGroupCode(sysAccount, SysDictConstants.GROUP_CODE.MSG_TYPE, SysDictConstants.MSG_TYPE.SMS_ACCOUNT_PWD);
        //所需加密DesKey
        String smsDeskey = sysDictMapper.selectByGroupCode(sysAccount, SysDictConstants.GROUP_CODE.MSG_TYPE, SysDictConstants.MSG_TYPE.SMS_DESKEY);
        Map<String, String> map = new HashMap<>(6);
        map.put("account", account);
        map.put("password", password);
        map.put("desKey", smsDeskey);
        map.put("url", url);
        map.put("mobile", mobile);
        map.put("content", mobile.startsWith("1") ? msg : (msg.replace("【", "[")).replace("】", "]"));
        Map<String, String> rtn = SmsApi.sendSms(SmsApi.SMS, map);
        logger.debug("SmsServiceImpl.send.sms.info :{} ", JsonUtils.toJson(rtn));
        return rtn;
    }

}
