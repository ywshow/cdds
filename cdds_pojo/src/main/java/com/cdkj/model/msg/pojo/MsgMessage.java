package com.cdkj.model.msg.pojo;


import com.cdkj.common.base.model.pojo.BaseModel;

public class MsgMessage extends BaseModel {

    private String userId;

    private String conId;

    private Integer conNo;

    private String conName;

    private String content;

    private Integer isRead;

    private String msgTypeId;

    private String msgType;

    public MsgMessage() {
    }

    public MsgMessage(String id, String userId, String sysAccount, String conId, Integer conNo, String conName, String content, Integer isRead, String msgTypeId, String msgType, String remark, String createDt) {
        this.id = id;
        this.userId = userId;
        this.sysAccount = sysAccount;
        this.conId = conId;
        this.conNo = conNo;
        this.conName = conName;
        this.content = content;
        this.isRead = isRead;
        this.msgTypeId = msgTypeId;
        this.msgType = msgType;
        this.remark = remark;
        this.setCreateDt(createDt);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId == null ? null : conId.trim();
    }

    public Integer getConNo() {
        return conNo;
    }

    public void setConNo(Integer conNo) {
        this.conNo = conNo;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName == null ? null : conName.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public String getMsgTypeId() {
        return msgTypeId;
    }

    public void setMsgTypeId(String msgTypeId) {
        this.msgTypeId = msgTypeId == null ? null : msgTypeId.trim();
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType == null ? null : msgType.trim();
    }
}
