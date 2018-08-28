package com.cdkj.model.msg.pojo;


import com.cdkj.common.base.model.pojo.BaseModel;

public class MsgTemplate extends BaseModel {

    private String tmplTitle;

    private String tmplName;

    private String tmplCode;

    private String tmplBody;

    private Integer sendSms;

    private Integer sendInnerMsg;

    private Integer sendPush;

    public String getTmplTitle() {
        return tmplTitle;
    }

    public void setTmplTitle(String tmplTitle) {
        this.tmplTitle = tmplTitle == null ? null : tmplTitle.trim();
    }

    public String getTmplName() {
        return tmplName;
    }

    public void setTmplName(String tmplName) {
        this.tmplName = tmplName == null ? null : tmplName.trim();
    }

    public String getTmplCode() {
        return tmplCode;
    }

    public void setTmplCode(String tmplCode) {
        this.tmplCode = tmplCode == null ? null : tmplCode.trim();
    }

    public String getTmplBody() {
        return tmplBody;
    }

    public void setTmplBody(String tmplBody) {
        this.tmplBody = tmplBody == null ? null : tmplBody.trim();
    }

    public Integer getSendSms() {
        return sendSms;
    }

    public void setSendSms(Integer sendSms) {
        this.sendSms = sendSms;
    }

    public Integer getSendInnerMsg() {
        return sendInnerMsg;
    }

    public void setSendInnerMsg(Integer sendInnerMsg) {
        this.sendInnerMsg = sendInnerMsg;
    }

    public Integer getSendPush() {
        return sendPush;
    }

    public void setSendPush(Integer sendPush) {
        this.sendPush = sendPush;
    }

}