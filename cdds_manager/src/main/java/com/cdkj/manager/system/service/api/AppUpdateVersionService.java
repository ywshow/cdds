/**
 * project name:saas
 * file name:AppUpdateVersionService
 * package name:com.cdkj.system.service.api
 * date:2018/4/2 14:04
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.service.api;


import com.cdkj.common.base.service.api.BaseService;
import com.cdkj.model.system.pojo.AppUpdateVersion;

import java.util.List;

/**
 * description: app版本管理<br>
 * date: 2018/4/2 14:04
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
public interface AppUpdateVersionService extends BaseService<AppUpdateVersion, String> {

    /**
     * description: 根据条件查询，无则查询全部 <br>
     *
     * @param model 对象信息(可空)
     * @return 返回数据列表
     */
    List<AppUpdateVersion> selectByCondition(AppUpdateVersion model);
}