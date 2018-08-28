package com.cdkj.model.system.pojo;

import com.cdkj.common.base.model.pojo.BaseModel;

/**
 * PackageName:com.cdkj.model.system.pojo
 * Descript:部门<br/>
 * date: 2018-6-19 <br/>
 * @author: yw
 * version 1.0
 */
public class SysDept extends BaseModel {

    private String parentId;

    private String deptName;

    private String deptCode;

    private Integer sortNumber;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

}