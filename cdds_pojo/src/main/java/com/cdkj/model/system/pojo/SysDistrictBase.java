package com.cdkj.model.system.pojo;


import com.cdkj.common.base.model.pojo.BaseModel;

import java.math.BigDecimal;

/**
 * PackageName:com.cdkj.model.system.pojo
 * Descript:基础地址<br/>
 * date: 2018-6-19 <br/>
 * @author: yw
 * version 1.0
 */
public class SysDistrictBase extends BaseModel {

    /**
     * Database Column Remarks:
     * 编号
     * database column sys_district_base.DC_ID
     */
    private Integer dcId;

    /**
     * Database Column Remarks:
     * 上级编号
     * database column sys_district_base.DC_P_ID
     */
    private Integer dcPId;

    /**
     * Database Column Remarks:
     * 名称
     * database column sys_district_base.NAME
     */
    private String name;

    /**
     * Database Column Remarks:
     * 全路径名称
     * database column sys_district_base.MERGER_NAME
     */
    private String mergerName;

    /**
     * Database Column Remarks:
     * 简称
     * database column sys_district_base.SHORT_NAME
     */
    private String shortName;

    /**
     * Database Column Remarks:
     * 全路径简称
     * database column sys_district_base.MERGER_SHORT_NAME
     */
    private String mergerShortName;

    /**
     * Database Column Remarks:
     * 层级
     * database column sys_district_base.LEVEL_TYPE
     */
    private Integer levelType;

    /**
     * Database Column Remarks:
     * 区号
     * database column sys_district_base.CITY_CODE
     */
    private Integer cityCode;

    /**
     * Database Column Remarks:
     * 邮编
     * database column sys_district_base.ZIP_CODE
     */
    private Integer zipCode;

    /**
     * Database Column Remarks:
     * 拼音
     * database column sys_district_base.PINYIN
     */
    private String pinyin;

    /**
     * Database Column Remarks:
     * 简拼
     * database column sys_district_base.JIANPIN
     */
    private String jianpin;

    /**
     * Database Column Remarks:
     * 首字母
     * database column sys_district_base.FIRST_CHAR
     */
    private String firstChar;

    /**
     * Database Column Remarks:
     * 经度
     * database column sys_district_base.LNG
     */
    private BigDecimal lng;

    /**
     * Database Column Remarks:
     * 纬度
     * database column sys_district_base.LAT
     */
    private BigDecimal lat;

    /**
     * database column sys_district_base.ID
     *
     * @return the value of sys_district_base.ID
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * database column sys_district_base.ID
     *
     * @param id the value for sys_district_base.ID
     */
    @Override
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * database column sys_district_base.DC_ID
     *
     * @return the value of sys_district_base.DC_ID
     */
    public Integer getDcId() {
        return dcId;
    }

    /**
     * database column sys_district_base.DC_ID
     *
     * @param dcId the value for sys_district_base.DC_ID
     */
    public void setDcId(Integer dcId) {
        this.dcId = dcId;
    }

    /**
     * database column sys_district_base.DC_P_ID
     *
     * @return the value of sys_district_base.DC_P_ID
     */
    public Integer getDcPId() {
        return dcPId;
    }

    /**
     * database column sys_district_base.DC_P_ID
     *
     * @param dcPId the value for sys_district_base.DC_P_ID
     */
    public void setDcPId(Integer dcPId) {
        this.dcPId = dcPId;
    }

    /**
     * database column sys_district_base.NAME
     *
     * @return the value of sys_district_base.NAME
     */
    public String getName() {
        return name;
    }

    /**
     * database column sys_district_base.NAME
     *
     * @param name the value for sys_district_base.NAME
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * database column sys_district_base.MERGER_NAME
     *
     * @return the value of sys_district_base.MERGER_NAME
     */
    public String getMergerName() {
        return mergerName;
    }

    /**
     * database column sys_district_base.MERGER_NAME
     *
     * @param mergerName the value for sys_district_base.MERGER_NAME
     */
    public void setMergerName(String mergerName) {
        this.mergerName = mergerName == null ? null : mergerName.trim();
    }

    /**
     * database column sys_district_base.SHORT_NAME
     *
     * @return the value of sys_district_base.SHORT_NAME
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * database column sys_district_base.SHORT_NAME
     *
     * @param shortName the value for sys_district_base.SHORT_NAME
     */
    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    /**
     * database column sys_district_base.MERGER_SHORT_NAME
     *
     * @return the value of sys_district_base.MERGER_SHORT_NAME
     */
    public String getMergerShortName() {
        return mergerShortName;
    }

    /**
     * database column sys_district_base.MERGER_SHORT_NAME
     *
     * @param mergerShortName the value for sys_district_base.MERGER_SHORT_NAME
     */
    public void setMergerShortName(String mergerShortName) {
        this.mergerShortName = mergerShortName == null ? null : mergerShortName.trim();
    }

    /**
     * database column sys_district_base.LEVEL_TYPE
     *
     * @return the value of sys_district_base.LEVEL_TYPE
     */
    public Integer getLevelType() {
        return levelType;
    }

    /**
     * database column sys_district_base.LEVEL_TYPE
     *
     * @param levelType the value for sys_district_base.LEVEL_TYPE
     */
    public void setLevelType(Integer levelType) {
        this.levelType = levelType;
    }

    /**
     * database column sys_district_base.CITY_CODE
     *
     * @return the value of sys_district_base.CITY_CODE
     */
    public Integer getCityCode() {
        return cityCode;
    }

    /**
     * database column sys_district_base.CITY_CODE
     *
     * @param cityCode the value for sys_district_base.CITY_CODE
     */
    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    /**
     * database column sys_district_base.ZIP_CODE
     *
     * @return the value of sys_district_base.ZIP_CODE
     */
    public Integer getZipCode() {
        return zipCode;
    }

    /**
     * database column sys_district_base.ZIP_CODE
     *
     * @param zipCode the value for sys_district_base.ZIP_CODE
     */
    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * database column sys_district_base.PINYIN
     *
     * @return the value of sys_district_base.PINYIN
     */
    public String getPinyin() {
        return pinyin;
    }

    /**
     * database column sys_district_base.PINYIN
     *
     * @param pinyin the value for sys_district_base.PINYIN
     */
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin == null ? null : pinyin.trim();
    }

    /**
     * database column sys_district_base.JIANPIN
     *
     * @return the value of sys_district_base.JIANPIN
     */
    public String getJianpin() {
        return jianpin;
    }

    /**
     * database column sys_district_base.JIANPIN
     *
     * @param jianpin the value for sys_district_base.JIANPIN
     */
    public void setJianpin(String jianpin) {
        this.jianpin = jianpin == null ? null : jianpin.trim();
    }

    /**
     * database column sys_district_base.FIRST_CHAR
     *
     * @return the value of sys_district_base.FIRST_CHAR
     */
    public String getFirstChar() {
        return firstChar;
    }

    /**
     * database column sys_district_base.FIRST_CHAR
     *
     * @param firstChar the value for sys_district_base.FIRST_CHAR
     */
    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar == null ? null : firstChar.trim();
    }

    /**
     * database column sys_district_base.LNG
     *
     * @return the value of sys_district_base.LNG
     */
    public BigDecimal getLng() {
        return lng;
    }

    /**
     * database column sys_district_base.LNG
     *
     * @param lng the value for sys_district_base.LNG
     */
    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    /**
     * database column sys_district_base.LAT
     *
     * @return the value of sys_district_base.LAT
     */
    public BigDecimal getLat() {
        return lat;
    }

    /**
     * database column sys_district_base.LAT
     *
     * @param lat the value for sys_district_base.LAT
     */
    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

}