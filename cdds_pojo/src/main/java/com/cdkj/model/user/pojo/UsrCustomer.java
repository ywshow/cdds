package com.cdkj.model.user.pojo;


import com.cdkj.common.base.model.pojo.BaseModel;

import java.math.BigDecimal;

public class UsrCustomer extends BaseModel {

    private String userId;

    private Integer conNo;

    private String conName;

    private String realName;

    private String password;

    private String salt;

    private String idCard;

    private String pic;

    private String mobile;

    private String email;

    private Byte gender;

    private String birthday;

    private String country;

    private String province;

    private String city;

    private String district;

    private String address;

    private String postalCode;

    private String sourceId;

    private String unionId;

    private String lastLoginDt;

    private Boolean subscribe;

    private String subscribeDt;

    private Boolean qrcodeType;

    private Integer conType;
    /**
     * 业务字段
     * @return
     */

    /**
     * 公众号名称
     */
    private String company;

    /**
     * 公众号二维码
     */
    private String compQrcode;

    /**
     * 账套LOGO图片信息地址
     */
    private String logo;

    /**
     * 可提现金额
     */
    private BigDecimal assetAvailable;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getConNo() {
        return conNo;
    }

    public void setConNo(Integer conNo) {
        this.conNo = conNo;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getLastLoginDt() {
        return lastLoginDt;
    }

    public void setLastLoginDt(String lastLoginDt) {
        this.lastLoginDt = lastLoginDt;
    }

    public Boolean getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Boolean subscribe) {
        this.subscribe = subscribe;
    }

    public String getSubscribeDt() {
        return subscribeDt;
    }

    public void setSubscribeDt(String subscribeDt) {
        this.subscribeDt = subscribeDt;
    }

    public Boolean getQrcodeType() {
        return qrcodeType;
    }

    public void setQrcodeType(Boolean qrcodeType) {
        this.qrcodeType = qrcodeType;
    }

    public Integer getConType() {
        return conType;
    }

    public void setConType(Integer conType) {
        this.conType = conType;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompQrcode() {
        return compQrcode;
    }

    public void setCompQrcode(String compQrcode) {
        this.compQrcode = compQrcode;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public BigDecimal getAssetAvailable() {
        return assetAvailable;
    }

    public void setAssetAvailable(BigDecimal assetAvailable) {
        this.assetAvailable = assetAvailable;
    }
}