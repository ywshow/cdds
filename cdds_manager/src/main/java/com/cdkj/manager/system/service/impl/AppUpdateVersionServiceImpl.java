/**
 * project name:saas
 * file name:AppUpdateVersionServiceImpl
 * package name:com.cdkj.system.service.impl
 * date:2018/4/2 14:07
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.service.impl;

import com.cdkj.common.base.service.impl.BaseServiceImpl;
import com.cdkj.common.exception.CustException;
import com.cdkj.util.JsonUtils;
import com.cdkj.constant.ErrorCode;
import com.cdkj.manager.system.dao.AppUpdateVersionMapper;
import com.cdkj.manager.system.service.api.AppUpdateVersionService;
import com.cdkj.model.system.pojo.AppUpdateVersion;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * description: app版本管理 <br>
 * date: 2018/4/2 14:07
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public class AppUpdateVersionServiceImpl extends BaseServiceImpl<AppUpdateVersion, String> implements AppUpdateVersionService {

    @Resource
    private AppUpdateVersionMapper updateVersionMapper;

    /**
     * description: 根据条件查询，无则查询全部 <br>
     *
     * @param model 对象信息(可空)
     * @return 返回数据列表
     */
    @Override
    public List<AppUpdateVersion> selectByCondition(AppUpdateVersion model) {
        Map<String,Object> map = JsonUtils.getMaptoFromJson(JsonUtils.toJson(model));
        if(map.isEmpty()){
            throw new CustException(ErrorCode.ERROR_20001,"参数为空");
        }
        return updateVersionMapper.selectByCondition(model);
    }
}