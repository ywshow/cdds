/**
 * project name:saas
 * file name:MsgMessageController
 * package name:com.cdkj.msg.controller
 * date:2018/3/28 16:10
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.msg.controller;

import com.cdkj.common.base.controller.BaseController;
import com.cdkj.common.exception.CustException;
import com.cdkj.util.JsonUtils;
import com.cdkj.constant.ErrorCode;
import com.cdkj.manager.msg.service.api.MsgMessageService;
import com.cdkj.model.msg.pojo.MsgMessage;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * description: 消息控制器 <br>
 * date: 2018/3/28 16:10
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@Api(value = "/msg/message", description = "消息控制器")
@RestController
@RequestMapping("/msg/open/message/")
public class MsgMessageController extends BaseController<MsgMessage> {

    @Resource
    private MsgMessageService msgMessageService;

    /**
     * description: 更改是否已读状态 <br>
     *
     * @param msgId  消息ID
     * @param isRead 是否已读【0：未读 1：已读】
     * @return 操作数量
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "msgId", value = "消息ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "isRead", value = "是否已读", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("update/{msgId}/{isRead}")
    public String updateByIsRead(@PathVariable("msgId") String msgId, @PathVariable("isRead") Integer isRead) {
        try {
            return JsonUtils.res(msgMessageService.updateByIsRead(msgId, isRead));
        } catch (CustException ce) {
            logger.error("MsgMessageController.updateByIsRead()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("MsgMessageController.updateByIsRead()方法系统异常!error={}", e);
            return JsonUtils.resFailed(225, ErrorCode.ERROR_20002, "01", "系统异常");
        }
    }

    /**
     * description: 根据用户ID批量更改是否已读状态 <br>
     *
     * @param userId 用户ID
     * @param isRead 是否已读【0：未读 1：已读】
     * @return 操作数量
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "isRead", value = "是否已读", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping("update/list/{userId}/{isRead}")
    public String updateListByUserIdAndIsRead(@PathVariable("userId") String userId, @PathVariable("isRead") Integer isRead) {
        try {
            int num = msgMessageService.updateListByUserIdAndIsRead(userId, isRead);
            return JsonUtils.res(num);
        } catch (CustException ce) {
            logger.error("MsgMessageController.updateListByUserIdAndIsRead()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("MsgMessageController.updateListByUserIdAndIsRead()方法系统异常!error={}", e);
            return JsonUtils.resFailed(225, ErrorCode.ERROR_20002, "01", "系统异常");
        }
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "isRead", value = "是否已读【0：未读 1：已读】(", required = true, dataType = "int"),
            @ApiImplicitParam(name = "msgType", value = "消息类型【0：站内信 1：短信】", dataType = "int"),
            @ApiImplicitParam(name = "pageNum", value = "页数", dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "页码", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("select/{userId}/{pageNum}/{pageSize}")
    public String selectByUserId(@PathVariable("userId") String userId, Integer isRead, String msgType, @PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        try {
            PageInfo<MsgMessage> pageInfo = msgMessageService.selectByUserId(userId, isRead, msgType, pageNum, pageSize);
            return JsonUtils.res(pageInfo);
        } catch (CustException ce) {
            logger.error("MsgMessageController.selectByUserId()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("MsgMessageController.selectByUserId()方法系统异常!error={}", e);
            return JsonUtils.resFailed(225, ErrorCode.ERROR_20002, "02", "系统异常");
        }
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "conId", value = "会员ID", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "content", value = "内容", required = true, dataType = "string"),
            @ApiImplicitParam(name = "isRead", value = "是否已读【0：未读 1：已读】", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "msgTypeId", value = "消息模板类型编号", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "msgType", value = "消息类型【0：站内信 1：短信】", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "path")
    })
    @PostMapping("add/{userId}/{conId}/{isRead}/{msgTypeId}/{msgType}")
    public String insertIntoMsgMessage(@PathVariable("userId") String userId, @PathVariable("conId") String conId,
                                       @RequestParam("content") String content, @PathVariable("isRead") Integer isRead,
                                       @PathVariable("msgTypeId") String msgTypeId, @PathVariable("msgType") String msgType,
                                       @RequestParam(value = "remark", required = false) String remark) {
        try {
            int num = msgMessageService.insertIntoMsgMessage(userId, conId, content, isRead, msgTypeId, msgType, remark);
            return JsonUtils.res(num);
        } catch (CustException ce) {
            logger.error("MsgMessageController.insertIntoMsgMessage()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("MsgMessageController.insertIntoMsgMessage()方法系统异常!error={}", e);
            return JsonUtils.resFailed(225, ErrorCode.ERROR_20002, "03", "系统异常");
        }
    }
}