package com.cdkj.model.system.pojo;


import com.cdkj.common.base.model.pojo.BaseModel;

/**
 * PackageName:com.cdkj.model.system.pojo
 * Descript:app版本<br/>
 * date: 2018-6-19 <br/>
 * @author: yw
 * version 1.0
 */
public class AppUpdateVersion extends BaseModel {

    /**
     * Database Column Remarks:
     *   设备类别 0 IOS  1 Android
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_update_version.TYPE
     *
     * @mbg.generated
     */
    private Integer type;

    /**
     * Database Column Remarks:
     *   客户端标识
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_update_version.CLIENT
     *
     * @mbg.generated
     */
    private String client;

    /**
     * Database Column Remarks:
     *   APP版本号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_update_version.VERSION_CODE
     *
     * @mbg.generated
     */
    private String versionCode;

    /**
     * Database Column Remarks:
     *   版本下载地址
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_update_version.VERSION_URL
     *
     * @mbg.generated
     */
    private String versionUrl;

    /**
     * Database Column Remarks:
     *   版本序号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_update_version.SORT
     *
     * @mbg.generated
     */
    private Integer sort;

    /**
     * Database Column Remarks:
     *   更新内容
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_update_version.UPDATE_CONTENT
     *
     * @mbg.generated
     */
    private String updateContent;

    /**
     * Database Column Remarks:
     *   强制更新标志 0非强制  1  强制
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_update_version.FORCE_TAG
     *
     * @mbg.generated
     */
    private Integer forceTag;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_update_version.TYPE
     *
     * @return the value of app_update_version.TYPE
     *
     * @mbg.generated
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_update_version.TYPE
     *
     * @param type the value for app_update_version.TYPE
     *
     * @mbg.generated
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_update_version.CLIENT
     *
     * @return the value of app_update_version.CLIENT
     *
     * @mbg.generated
     */
    public String getClient() {
        return client;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_update_version.CLIENT
     *
     * @param client the value for app_update_version.CLIENT
     *
     * @mbg.generated
     */
    public void setClient(String client) {
        this.client = client == null ? null : client.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_update_version.VERSION_CODE
     *
     * @return the value of app_update_version.VERSION_CODE
     *
     * @mbg.generated
     */
    public String getVersionCode() {
        return versionCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_update_version.VERSION_CODE
     *
     * @param versionCode the value for app_update_version.VERSION_CODE
     *
     * @mbg.generated
     */
    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode == null ? null : versionCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_update_version.VERSION_URL
     *
     * @return the value of app_update_version.VERSION_URL
     *
     * @mbg.generated
     */
    public String getVersionUrl() {
        return versionUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_update_version.VERSION_URL
     *
     * @param versionUrl the value for app_update_version.VERSION_URL
     *
     * @mbg.generated
     */
    public void setVersionUrl(String versionUrl) {
        this.versionUrl = versionUrl == null ? null : versionUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_update_version.SORT
     *
     * @return the value of app_update_version.SORT
     *
     * @mbg.generated
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_update_version.SORT
     *
     * @param sort the value for app_update_version.SORT
     *
     * @mbg.generated
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_update_version.UPDATE_CONTENT
     *
     * @return the value of app_update_version.UPDATE_CONTENT
     *
     * @mbg.generated
     */
    public String getUpdateContent() {
        return updateContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_update_version.UPDATE_CONTENT
     *
     * @param updateContent the value for app_update_version.UPDATE_CONTENT
     *
     * @mbg.generated
     */
    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent == null ? null : updateContent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_update_version.FORCE_TAG
     *
     * @return the value of app_update_version.FORCE_TAG
     *
     * @mbg.generated
     */
    public Integer getForceTag() {
        return forceTag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_update_version.FORCE_TAG
     *
     * @param forceTag the value for app_update_version.FORCE_TAG
     *
     * @mbg.generated
     */
    public void setForceTag(Integer forceTag) {
        this.forceTag = forceTag;
    }

}