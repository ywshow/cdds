package com.cdkj.model.system.pojo;

import com.cdkj.common.base.model.pojo.BaseModel;

import java.util.List;

/**
 * PackageName:com.cdkj.model.system.pojo
 * Descript:用户<br/>
 * date: 2018-6-19 <br/>
 * @author: yw
 * version 1.0
 */
public class SysUser extends BaseModel {

    private String relationId;

    private String username;

    private String nickName;

    private String pic;

    private String password;

    private String salt;

    private String lastLoginDt;

    private Integer isLogin;

    private String mobile;

    private String mac;

    private Integer sourceLogin;

    private List<String> roleIds;
    private SysDept sysDept;

    public Integer getSourceLogin() {
        return sourceLogin;
    }

    public void setSourceLogin(Integer sourceLogin) {
        this.sourceLogin = sourceLogin;
    }

    public SysDept getSysDept() {
        return sysDept;
    }

    public void setSysDept(SysDept sysDept) {
        this.sysDept = sysDept;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId == null ? null : relationId.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getLastLoginDt() {
        return lastLoginDt;
    }

    public void setLastLoginDt(String lastLoginDt) {
        this.lastLoginDt = lastLoginDt == null ? null : lastLoginDt.trim();
    }

    public Integer getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Integer isLogin) {
        this.isLogin = isLogin;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac == null ? null : mac.trim();
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }
}