package com.cdkj.model.msg.pojo;


import com.cdkj.common.base.model.pojo.BaseModel;

public class MsgIdentifyCode extends BaseModel {

    private String userId;

    private String mobile;

    private String identifyCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getIdentifyCode() {
        return identifyCode;
    }

    public void setIdentifyCode(String identifyCode) {
        this.identifyCode = identifyCode == null ? null : identifyCode.trim();
    }
}