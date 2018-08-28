/**
 * project name:saas
 * file name:MsgMessageService
 * package name:com.cdkj.msg.service.api
 * date:2018/3/28 14:50
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.msg.service.api;

import com.cdkj.common.base.service.api.BaseService;
import com.cdkj.model.msg.pojo.MsgMessage;
import com.github.pagehelper.PageInfo;

/**
 * description: 消息<br>
 * date: 2018/3/28 14:50
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
public interface MsgMessageService extends BaseService<MsgMessage, String> {

    /**
     * description: 更改是否已读状态 <br>
     *
     * @param msgId  消息ID
     * @param isRead 是否已读【0：未读 1：已读】
     * @return 操作数量
     */
    int updateByIsRead(String msgId, Integer isRead);

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
    PageInfo<MsgMessage> selectByUserId(String userId, Integer isRead, String msgType, int pageNum, int pageSize);

    /**
     * description: 更改是否已读状态 <br>
     *
     * @param userId 用户ID
     * @param isRead 是否已读【0：未读 1：已读】
     * @return 操作数量
     */
    int updateListByUserIdAndIsRead(String userId, Integer isRead);

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
    int insertIntoMsgMessage(String userId, String conId, String content, Integer isRead, String msgTypeId, String msgType, String remark);
}