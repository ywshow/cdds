package com.cdkj.model.system.pojo;

import com.cdkj.common.base.model.pojo.BaseModel;

import java.util.List;

/**
 * PackageName:com.cdkj.model.system.pojo
 * Descript:角色<br/>
 * date: 2018-6-19 <br/>
 * @author: yw
 * version 1.0
 */
public class SysRole extends BaseModel {
    /**
     * 是否选中
     */
    private boolean roleSign;

    private String roleName;

    private String roleCode;

    private String roleDesc;

    private List<String> permissionIds;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc == null ? null : roleDesc.trim();
    }

    public List<String> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<String> permissionIds) {
        this.permissionIds = permissionIds;
    }

    public boolean isRoleSign() {
        return roleSign;
    }

    public void setRoleSign(boolean roleSign) {
        this.roleSign = roleSign;
    }
}