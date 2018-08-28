/**
 * Copyright (c) 2011-2015, Unas 小强哥 (unas@qq.com).
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.cdkj.manager.wechat.util.msg.in.event;

/**
 * 由于群发任务提交后，群发任务可能在一定时间后才完成，因此，群发接口调用时，仅会给出群发任务是否提交成功的提示，
 * 若群发任务提交成功，则在群发任务结束时，会向开发者在公众平台填写的开发者URL（callback URL）推送事件。
 * <pre>
 * &lt;xml&gt;
 * &lt;ToUserName&gt;&lt;![CDATA[gh_7f083739789a]]&gt;&lt;/ToUserName&gt;
 * &lt;FromUserName&gt;&lt;![CDATA[oia2TjuEGTNoeX76QEjQNrcURxG8]]&gt;&lt;/FromUserName&gt;
 * &lt;CreateTime&gt;1395658920&lt;/CreateTime&gt;
 * &lt;MsgType&gt;&lt;![CDATA[event]]&gt;&lt;/MsgType&gt;
 * &lt;Event&gt;&lt;![CDATA[MASSSENDJOBFINISH]]&gt;&lt;/Event&gt;
 * &lt;MsgID&gt;1988&lt;/MsgID&gt;
 * &lt;Status&gt;&lt;![CDATA[sendsuccess]]&gt;&lt;/Status&gt;
 * &lt;TotalCount&gt;100&lt;/TotalCount&gt;
 * &lt;FilterCount&gt;80&lt;/FilterCount&gt;
 * &lt;SentCount&gt;75&lt;/SentCount&gt;
 * &lt;ErrorCount&gt;5&lt;/ErrorCount&gt;
 * &lt;/xml&gt;
 * </pre>
 */
@SuppressWarnings("serial")
public class InMassEvent extends EventInMsg {
    //群发成功
    public static final String EVENT_INMASS_STATUS_SENDSUCCESS = "sendsuccess";
    //群发失败，其他失败情况是err(num)
    public static final String EVENT_INMASS_STATUS_SENDFAIL = "sendfail";

    private String msgId;
    private String status;
    private String totalCount;
    private String filterCount;
    private String sentCount;
    private String errorCount;

    public InMassEvent(String toUserName, String fromUserName, Integer createTime, String event) {
        super(toUserName, fromUserName, createTime, event);
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getFilterCount() {
        return filterCount;
    }

    public void setFilterCount(String filterCount) {
        this.filterCount = filterCount;
    }

    public String getSentCount() {
        return sentCount;
    }

    public void setSentCount(String sentCount) {
        this.sentCount = sentCount;
    }

    public String getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(String errorCount) {
        this.errorCount = errorCount;
    }
}

