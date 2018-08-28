package com.cdkj.model.system.pojo;

import com.cdkj.common.base.model.pojo.BaseModel;

/**
 * PackageName:com.cdkj.model.system.pojo
 * Descript:权限<br/>
 * date: 2018-6-19 <br/>
 * @author: yw
 * version 1.0
 */
public class SysPermission extends BaseModel {

    private String parentId;

    private String perName;

    private String perCode;

    private String perDesc;

    private String perUrl;

    private String perType;

    private String perIcon;

    private Integer sortNumber;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getPerName() {
        return perName;
    }

    public void setPerName(String perName) {
        this.perName = perName == null ? null : perName.trim();
    }

    public String getPerCode() {
        return perCode;
    }

    public void setPerCode(String perCode) {
        this.perCode = perCode == null ? null : perCode.trim();
    }

    public String getPerDesc() {
        return perDesc;
    }

    public void setPerDesc(String perDesc) {
        this.perDesc = perDesc == null ? null : perDesc.trim();
    }

    public String getPerUrl() {
        return perUrl;
    }

    public void setPerUrl(String perUrl) {
        this.perUrl = perUrl == null ? null : perUrl.trim();
    }

    public String getPerType() {
        return perType;
    }

    public void setPerType(String perType) {
        this.perType = perType == null ? null : perType.trim();
    }

    public String getPerIcon() {
        return perIcon;
    }

    public void setPerIcon(String perIcon) {
        this.perIcon = perIcon == null ? null : perIcon.trim();
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

}