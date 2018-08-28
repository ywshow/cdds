package com.cdkj.model.system.pojo;

import com.cdkj.common.base.model.pojo.BaseModel;

/**
 * PackageName:com.cdkj.model.system.pojo
 * Descript:用户角色<br/>
 * date: 2018-6-19 <br/>
 * @author: yw
 * version 1.0
 */
public class SysUserRole extends BaseModel {

    private String userId;

    private String roleId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

}