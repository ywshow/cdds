/**
 * project name:ach
 * file name:FileService
 * package name:com.cdkj.file.api
 * date:2017-08-31 13:47
 * author:yw
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.file.service.api;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * description: 文件管理服务层
 * date: 2017-08-31 13:47
 *
 * @author yw
 * @version 1.0
 * @since JDK 1.8
 */
public interface FileService {
    /**
     * 上传文件
     *
     * @param file      文件
     * @param groupName 组名
     * @param isCut     是否进行图片压缩：0否,1是
     * @return
     */
    String upload(MultipartFile file, String groupName, String isCut) throws Exception;

    /**
     * 获取token
     *
     * @return
     * @throws Exception
     */
    String getToken() throws Exception;

    /**
     * 多文件上传
     *
     * @param files 文件数组
     * @return
     */
    List<String> uploadFiles(MultipartFile[] files) throws Exception;
}
