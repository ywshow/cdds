/**
 * project name:saas
 * file name:UsrCustomerService
 * package name:com.cdkj.system.service.api
 * date:2018/4/1 7:40
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.service.api;

import com.cdkj.manager.system.service.hystric.UsrCustomerServiceHystricImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * description: //TODO <br>
 * date: 2018/4/1 7:40
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@FeignClient(value = "service-user-center", fallback = UsrCustomerServiceHystricImpl.class)
public interface UsrCustomerService {

    /**
     * description: 根据手机号更新用户ID <br>
     *
     * @param userId 会员ID
     * @param mobile 手机号
     * @return int
     */
    @PostMapping("/user/customer/update/userId/{userId}/{mobile}")
    String updateUserIdByMobile(@PathVariable("userId") String userId, @PathVariable("mobile") String mobile);

    /**
     * 根据SysAccount计算会员等级
     *
     * @param sysAccount
     * @return
     */
    @PostMapping("/computCustomerLevelByAccount/{sysAccount}")
    String computCustomerLevelByAccount(String sysAccount);

    /**
     * description: 根据手会员ID查询 <br>
     *
     * @param conId 会员ID
     * @return int
     */
    @GetMapping("/user/customer/selectById/{conId}")
    String selectById(@PathVariable("conId") String conId);
}