/**
 * project name:cdds
 * file name:TreeNode
 * package name:com.cdkj.ztree
 * date:2018/7/30 15:27
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.ztree;

import java.util.List;

/**
 * description: ztree树属性 <br>
 * date: 2018/7/30 15:27
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
public class TreeNode {
    /**
     * 主键
     */
    public String id;
    /**
     * 父节点
     */
    public String pId;
    /**
     * 节点名称
     */
    public String name;
    /**
     * 图标class名称
     */
    public String iconSkin;

    /**
     * 是否选中
     * true 表示节点的输入框被勾选
     * false 表示节点的输入框未勾选
     */
    public boolean checked;

    /**
     * 设置节点的 checkbox / radio 是否禁用
     * true 表示此节点的 checkbox / radio 被禁用。
     * false 表示此节点的 checkbox / radio 可以使用。
     */
    public boolean chkDisabled;

    /**
     * 是否父节点
     * true 表示是父节点
     * false 表示不是父节点
     */
    public boolean isParent;

    /**
     * 设置节点是否隐藏 checkbox / radio
     * true 表示此节点不显示 checkbox / radio，不影响勾选的关联关系，不影响父节点的半选状态
     * false 表示节点具有正常的勾选功能
     */
    public boolean nocheck;

    /**
     * true 表示节点为 展开 状态
     * false 表示节点为 折叠 状态
     */
    public boolean open;

    public List<TreeNode> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconSkin() {
        return iconSkin;
    }

    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public boolean isChkDisabled() {
        return chkDisabled;
    }

    public void setChkDisabled(boolean chkDisabled) {
        this.chkDisabled = chkDisabled;
    }

    public boolean isNocheck() {
        return nocheck;
    }

    public void setNocheck(boolean nocheck) {
        this.nocheck = nocheck;
    }
}