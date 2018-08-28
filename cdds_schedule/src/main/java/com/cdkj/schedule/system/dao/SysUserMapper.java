package com.cdkj.schedule.system.dao;

import com.cdkj.common.base.model.dao.BaseMapper;
import com.cdkj.model.system.pojo.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface SysUserMapper extends BaseMapper<SysUser, String> {
    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    SysUser selectByUsername(@Param(value = "username") String username);

    /**
     * 根据用户名及登录源获取用户信息
     *
     * @param username    用户名
     * @param sourceLogin 登录源：0:APP登录，1:后端登录
     * @return 用户信息
     */
    SysUser selectByUsernameAndSourceLogin(@Param(value = "username") String username, @Param(value = "sourceLogin") int sourceLogin);

    /**
     * 根据用户名及登录源获取用户信息
     *
     * @param mobile      手机号
     * @param sourceLogin 登录源：0:APP登录，1:后端登录
     * @return 用户信息
     */
    SysUser selectByMobileAndSourceLogin(@Param(value = "mobile") String mobile, @Param(value = "sourceLogin") int sourceLogin);

}