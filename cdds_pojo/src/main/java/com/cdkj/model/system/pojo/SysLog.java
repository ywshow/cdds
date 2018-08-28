package com.cdkj.model.system.pojo;

import com.cdkj.common.base.model.pojo.BaseModel;

/**
 * PackageName:com.cdkj.model.system.pojo
 * Descript:日志<br/>
 * date: 2018-6-19 <br/>
 * @author: yw
 * version 1.0
 */
public class SysLog extends BaseModel {

    private String userId;

    private String username;

    private String operation;

    private Integer time;

    private String method;

    private String params;

    private String ip;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

}