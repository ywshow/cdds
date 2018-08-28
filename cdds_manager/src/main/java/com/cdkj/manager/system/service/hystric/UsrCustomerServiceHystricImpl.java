/**
 * project name:saas
 * file name:UsrCustomerServiceHystricImpl
 * package name:com.cdkj.system.service.hystric
 * date:2018/4/1 7:41
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.service.hystric;

import com.cdkj.util.JsonUtils;
import com.cdkj.manager.system.service.api.UsrCustomerService;
import org.springframework.stereotype.Component;

/**
 * description: //TODO <br>
 * date: 2018/4/1 7:41
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@Component
public class UsrCustomerServiceHystricImpl implements UsrCustomerService {
    /**
     * description: 根据手机号更新用户ID <br>
     *
     * @param userId 会员ID
     * @param mobile 手机号
     * @return 异常结果
     */
    @Override
    public String updateUserIdByMobile(String userId, String mobile) {
        return JsonUtils.resFailed("根据手机号更新用户ID失败,请稍后再试");
    }

    @Override
    public String computCustomerLevelByAccount(String sysAccount) {
        return JsonUtils.resFailed("操作失败,请稍后再试");
    }

    /**
     * description: 根据手会员ID查询 <br>
     *
     * @param conId 会员ID
     * @return int
     */
    @Override
    public String selectById(String conId) {
        return JsonUtils.resFailed("操作失败,请稍后再试");
    }
}