/**
 * project name:ach
 * file name:TldIdentifyCodeServiceImpl
 * package name:com.cdkj.system.service.impl
 * date:2017-08-25 14:04
 * author:yw
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.msg.service.impl;

import com.cdkj.common.base.service.impl.BaseServiceImpl;
import com.cdkj.constant.MsgTmplConstant;
import com.cdkj.constant.SysDictConstants;
import com.cdkj.manager.msg.dao.MsgIdentifyCodeMapper;
import com.cdkj.manager.msg.dao.MsgTemplateMapper;
import com.cdkj.manager.msg.service.api.MsgIdentifyCodeService;
import com.cdkj.manager.msg.service.api.SmsService;
import com.cdkj.manager.system.dao.SysDictMapper;
import com.cdkj.model.msg.pojo.MsgIdentifyCode;
import com.cdkj.model.msg.pojo.MsgTemplate;
import com.cdkj.util.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * description: 用户验证码管理实现层
 * date: 2017-08-25 14:04
 *
 * @author yw
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public class MsgIdentifyCodeServiceImpl extends BaseServiceImpl<MsgIdentifyCode, String> implements MsgIdentifyCodeService {
    // 验证码默认有效20分钟
    private static Integer VALID_TIME = 20;
    // 验证码默认长度6个字符
    private static Integer VALID_LENGTH = 6;
    // 验证码默认类型为'0':表示仅获得数字随机数
    private static Integer VALID_TYPE = 0;
    @Resource
    private MsgIdentifyCodeMapper msgIdentifyCodeMapper;
    @Resource
    private MsgTemplateMapper msgTemplateMapper;
    @Resource
    private SysDictMapper sysDictMapper;
    @Resource
    private SmsService smsService;

    /**
     * 发送验证码
     *
     * @param mobile 手机号码
     * @return
     */
    @Override
    public Map<String, Object> send(String sysAccount, String mobile) {
        MsgIdentifyCode msgIdentifyCode = new MsgIdentifyCode();
        msgIdentifyCode.setId(UUIDGenerator.randomUUID());
        msgIdentifyCode.setMobile(mobile);
        msgIdentifyCode.setSysAccount(sysAccount);
        msgIdentifyCode.setCreateDt(DateUtil.getNow());
        msgIdentifyCode.setUpdateDt(DateUtil.getNow());
        //获取随机码
        //默认6位长度
        int length = VALID_LENGTH;
        //默认类型'0':表示仅获得数字随机数
        int type = VALID_TYPE;
        //默认20分钟有效时间
        Integer validTime = VALID_TIME;
        MsgIdentifyCode otherIdentifyCode = msgIdentifyCodeMapper.selectByMobile(sysAccount, mobile);

        if (!ObjectUtils.isEmpty(otherIdentifyCode)) {
            long timeDiff = DateUtil.getTimeDiff(DateUtil.getNow(), otherIdentifyCode.getCreateDt(), DateUtil.DIFF_UNIT_MIN);
            if (timeDiff > validTime) {
                //如果过期了,则变更为失效
                otherIdentifyCode.setEnabled((short) 0);
                msgIdentifyCodeMapper.updateByPrimaryKeySelective(otherIdentifyCode);
            }
        }
        msgIdentifyCode.setIdentifyCode(RandomValidateCode.CreateRadom(length, type));
        int executeInt = msgIdentifyCodeMapper.insertSelective(msgIdentifyCode);
        /*数据操作成功，给用户发送短信*/
        if (executeInt > 0) {
            sendMsg(msgIdentifyCode, validTime);
        }

        return ResultUtil.getResult("发送成功！");
    }

    /**
     * 验证验证码
     *
     * @param mobile 手机号码
     * @param code   验证码
     * @return
     */
    @Override
    public boolean checkIdentifyCode(String sysAccount, String mobile, String code) {
        List<MsgIdentifyCode> list = msgIdentifyCodeMapper.listByMobile(sysAccount, mobile);
        if (list.isEmpty()) {
            return false;
        }
        for (MsgIdentifyCode item : list) {
            long timeDiff = DateUtil.getTimeDiff(DateUtil.getNow(), item.getCreateDt(), DateUtil.DIFF_UNIT_MIN);
            if (timeDiff > VALID_TIME) {
                //如果过期了，直接变更为失效, 且继续循环下一个
                item.setEnabled((short) 0);
                msgIdentifyCodeMapper.updateByPrimaryKeySelective(item);
            } else {
                // 找到符合有效期的, 且需要进行验证码校验
                if (item.getIdentifyCode().equals(code)) {
                    item.setEnabled((short) 0);
                    msgIdentifyCodeMapper.updateByPrimaryKeySelective(item);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 调用api，给用户发送短信code
     *
     * @param identifyCode
     */
    private void sendMsg(MsgIdentifyCode identifyCode, Integer validTime) {
        String code = identifyCode.getIdentifyCode();
        String mobile = identifyCode.getMobile();
        String content = getSmsContent(identifyCode.getSysAccount(), validTime, code);
        Map<String, String> rtn = smsService.send(identifyCode.getSysAccount(), mobile, content);
        //短信发送类型 - SMS微服务
        /*如果发送失败，则标记数据库,变更为失效*/
        if ("-1".equals(rtn.get("resultCode"))) {
            identifyCode.setEnabled((short) 0);
            msgIdentifyCodeMapper.updateByPrimaryKeySelective(identifyCode);
        }

    }

    /**
     * 生成短信内容
     *
     * @param validTime 短信有效时间
     * @param code      短信验证码
     * @return
     */
    private String getSmsContent(String sysAccount, Integer validTime, String code) {
        MsgTemplate tmpl = msgTemplateMapper.selectByTemplateCode(sysAccount, MsgTmplConstant.MSG_TMPL_REG_LOG_IDENTIFY_CODE);
        return String.format(tmpl.getTmplBody(), code, validTime) + sysDictMapper.selectByGroupCode(sysAccount, SysDictConstants.GROUP_CODE.MSG_TYPE, SysDictConstants.MSG_TYPE.SMS_SIGN);
    }
}
