/**
 * project name:cdds
 * file name:LoadToRedis
 * package name:com.cdkj.manager.runload
 * date:2018/8/23 15:20
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.runload;

import com.cdkj.common.base.controller.BaseController;
import com.cdkj.common.constant.Constant;
import com.cdkj.manager.system.service.api.SysAccountService;
import com.cdkj.manager.system.service.api.SysDictBaseDetailService;
import com.cdkj.manager.system.service.api.SysDictService;
import com.cdkj.model.system.pojo.SysAccount;
import com.cdkj.model.system.pojo.SysDict;
import com.cdkj.model.system.pojo.SysDictBaseDetail;
import com.cdkj.util.StringUtil;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * description: //TODO <br>
 * date: 2018/8/23 15:20
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@Component
public class LoadToRedis extends BaseController implements ApplicationListener {

    @Resource
    private SysDictService sysDictService;

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        SysDict sysDict = new SysDict();
        sysDict.setEnabled(Constant.ENABLED_Y);
        List<SysDict> list = sysDictService.listByPrimaryKeySelective(sysDict);
        list.stream().forEach(item -> {
            if (StringUtil.isNotEmpty(item.getParamValue())) {
//                System.out.println(">>>" + item.toString());
            }
        });
    }
}