package com.cdkj.model.user.pojo;

import com.cdkj.common.base.model.pojo.BaseModel;

public class UsrCustomerRel extends BaseModel {

    private String conId;

    private String rpId;

    private String rppId;

    private String rpppId;

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getRpId() {
        return rpId;
    }

    public void setRpId(String rpId) {
        this.rpId = rpId;
    }

    public String getRppId() {
        return rppId;
    }

    public void setRppId(String rppId) {
        this.rppId = rppId;
    }

    public String getRpppId() {
        return rpppId;
    }

    public void setRpppId(String rpppId) {
        this.rpppId = rpppId;
    }

}