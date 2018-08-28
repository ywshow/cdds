package com.cdkj.model.system.pojo;


import com.cdkj.common.base.model.pojo.BaseModel;

/**
 * PackageName:com.cdkj.model.system.pojo
 * Descript:数据字典基准<br/>
 * date: 2018-6-19 <br/>
 * @author: yw
 * version 1.0
 */
public class SysDictBase extends BaseModel {

    private String groupName;

    private String groupCode;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode == null ? null : groupCode.trim();
    }

}