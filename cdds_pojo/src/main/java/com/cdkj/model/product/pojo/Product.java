package com.cdkj.model.product.pojo;

public class Product {
    private Short name;

    private String sysAccount;

    private String loc;

    public Short getName() {
        return name;
    }

    public void setName(Short name) {
        this.name = name;
    }

    public String getSysAccount() {
        return sysAccount;
    }

    public void setSysAccount(String sysAccount) {
        this.sysAccount = sysAccount == null ? null : sysAccount.trim();
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc == null ? null : loc.trim();
    }
}