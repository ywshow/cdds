package com.cdkj.common.base.model.pojo;

import com.cdkj.util.JsonUtils;

import java.io.Serializable;

/**
 * PackageName:com.cdkj.base
 * Descript:Model基类，包含公共字段信息<br/>
 * date: 2016-2-19 <br/>
 * User: bovine
 * version 1.0
 */
public class BaseModel implements Serializable {

    private static final long serialVersionUID = 4190794195498682067L;

    protected String id;
    protected String createBy;
    protected String createDt;
    protected String updateDt;
    protected String updateBy;
    /**
     * 版本
     **/
    protected Integer version;
    /**
     * 帐套
     **/
    protected String sysAccount;
    /**
     * 业务状态
     **/
    protected Short status;
    /**
     * 数据有效性 0,失效，1有效
     **/
    protected Short enabled;
    /**
     * 业务备注字段
     **/
    protected String remark;
    /**
     * 备注字段，不作为业务字段使用
     **/
    protected String memo;
    /**
     * 顶层ID，用于将数据树型处理
     **/
    protected String firstId;
    /**
     * 渠道ID
     **/
    protected String channelId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getSysAccount() {
        return sysAccount;
    }

    public void setSysAccount(String sysAccount) {
        this.sysAccount = sysAccount;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getEnabled() {
        return enabled;
    }

    public void setEnabled(Short enabled) {
        this.enabled = enabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getFirstId() {
        return firstId;
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
