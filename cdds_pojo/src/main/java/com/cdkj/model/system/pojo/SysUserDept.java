package com.cdkj.model.system.pojo;

import com.cdkj.common.base.model.pojo.BaseModel;

/**
 * PackageName:com.cdkj.model.system.pojo
 * Descript:用户部门<br/>
 * date: 2018-6-19 <br/>
 * @author: yw
 * version 1.0
 */
public class SysUserDept extends BaseModel {

    private String userId;

    private String deptId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId == null ? null : deptId.trim();
    }

}