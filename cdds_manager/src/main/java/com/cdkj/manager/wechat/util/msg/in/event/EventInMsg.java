/**
 * Copyright (c) 2011-2015, Unas 小强哥 (unas@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.cdkj.manager.wechat.util.msg.in.event;


import com.cdkj.manager.wechat.util.msg.in.InMsg;

@SuppressWarnings("serial")
public abstract class EventInMsg extends InMsg {
    private static final String MSG_TYPE = "event";
    protected String event;

    public EventInMsg(String toUserName, String fromUserName, Integer createTime, String event) {
        super(toUserName, fromUserName, createTime, MSG_TYPE);
        this.event = event;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
