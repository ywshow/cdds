/**
 * project name:saas
 * file name:MsgMessageServiceImpl
 * package name:com.cdkj.msg.service.impl
 * date:2018/3/28 14:55
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.msg.service.impl;

import com.cdkj.common.base.service.impl.BaseServiceImpl;
import com.cdkj.common.constant.ResultCode;
import com.cdkj.common.exception.CustException;
import com.cdkj.util.DateUtil;
import com.cdkj.util.JsonUtils;
import com.cdkj.constant.ErrorCode;
import com.cdkj.manager.msg.dao.MsgMessageMapper;
import com.cdkj.manager.msg.service.api.MsgMessageService;
import com.cdkj.manager.system.service.api.UsrCustomerService;
import com.cdkj.model.msg.pojo.MsgMessage;
import com.cdkj.util.UUIDGenerator;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import com.cdkj.model.user.pojo.UsrCustomer;

import javax.annotation.Resource;
import java.util.List;

/**
 * description: 消息模块 <br>
 * date: 2018/3/28 14:55
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public class MsgMessageServiceImpl extends BaseServiceImpl<MsgMessage, String> implements MsgMessageService {

    @Resource
    private MsgMessageMapper msgMessageMapper;

    @Resource
    private UsrCustomerService usrCustomerService;

    /**
     * description: 更改是否已读状态 <br>
     *
     * @param msgId  消息ID
     * @param isRead 是否已读【0：未读 1：已读】
     * @return 操作数量
     */
    @Override
    public int updateByIsRead(String msgId, Integer isRead) {
        if (StringUtils.isEmpty(msgId)) {
            throw new CustException(ErrorCode.ERROR_20001, "消息ID为空");
        }
        if (StringUtils.isEmpty(isRead)) {
            throw new CustException(ErrorCode.ERROR_20001, "状态为空");
        }
        MsgMessage msgMessage = new MsgMessage();
        msgMessage.setId(msgId);
        msgMessage.setIsRead(isRead);
        return msgMessageMapper.updateByPrimaryKeySelective(msgMessage);
    }

    /**
     * description: 根据用户ID和是否已读查询 <br>
     *
     * @param userId   用户ID
     * @param isRead   是否已读【0：未读 1：已读】(可空)
     * @param msgType  消息类型【0：站内信 1：短信】(可空)
     * @param pageNum  页数
     * @param pageSize 页码
     * @return 返回查询列表
     */
    @Override
    public PageInfo<MsgMessage> selectByUserId(String userId, Integer isRead, String msgType, int pageNum, int pageSize) {
        if (StringUtils.isEmpty(userId)) {
            throw new CustException(ErrorCode.ERROR_20001, "用户ID为空");
        }
        Page<MsgMessage> page = PageHelper.startPage(pageNum, pageSize);
        List<MsgMessage> list = msgMessageMapper.selectByUserId(userId, isRead, msgType);
        PageInfo pageInfo = new PageInfo(list);
        page.setTotal(page.getTotal());
        return pageInfo;
    }

    /**
     * description: 更改是否已读状态 <br>
     *
     * @param userId 用户ID
     * @param isRead 是否已读【0：未读 1：已读】
     * @return 操作数量
     */
    @Override
    public int updateListByUserIdAndIsRead(String userId, Integer isRead) {
        if (StringUtils.isEmpty(userId)) {
            throw new CustException(ErrorCode.ERROR_20001, "用户ID为空");
        }
        if (StringUtils.isEmpty(isRead)) {
            throw new CustException(ErrorCode.ERROR_20001, "是否已读状态为空");
        }
        return msgMessageMapper.updateListByUserIdAndIsRead(userId, isRead);
    }

    /**
     * description: 新增消息 <br>
     *
     * @param userId    用户ID
     * @param conId     会员ID
     * @param content   内容
     * @param isRead    是否已读【0：未读 1：已读】
     * @param msgTypeId 消息模板类型编号
     * @param msgType   消息类型【0：站内信 1：短信】
     * @param remark    备注
     * @return 操作数量
     */
    @Override
    public int insertIntoMsgMessage(String userId, String conId, String content, Integer isRead, String msgTypeId, String msgType, String remark) {
        if (StringUtils.isEmpty(userId)) {
            throw new CustException(ErrorCode.ERROR_20001, "用户ID为空");
        }
        if (StringUtils.isEmpty(conId)) {
            throw new CustException(ErrorCode.ERROR_20001, "会员ID为空");
        }
        if (StringUtils.isEmpty(content)) {
            throw new CustException(ErrorCode.ERROR_20001, "内容为空");
        }
        if (StringUtils.isEmpty(isRead)) {
            throw new CustException(ErrorCode.ERROR_20001, "已读状态为空");
        }
        if (StringUtils.isEmpty(msgTypeId)) {
            throw new CustException(ErrorCode.ERROR_20001, "消息模板类型编号为空");
        }
        if (StringUtils.isEmpty(msgType)) {
            throw new CustException(ErrorCode.ERROR_20001, "消息发送方式为空");
        }
        String result = usrCustomerService.selectById(conId);
        UsrCustomer usrCustomer = JsonUtils.getObjectFromResultJson(result, UsrCustomer.class);
        if (ObjectUtils.isEmpty(usrCustomer)) {
            throw new CustException(ResultCode.NO_DATA, "会员信息为空");
        }
        //初始化数据
        MsgMessage msgMessage = new MsgMessage(UUIDGenerator.randomUUID(), userId, usrCustomer.getSysAccount(),
                conId, usrCustomer.getConNo(), usrCustomer.getConName(), content, isRead, msgTypeId,
                msgType, remark, DateUtil.getNow());
        return msgMessageMapper.insertSelective(msgMessage);
    }
}