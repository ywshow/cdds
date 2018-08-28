package com.cdkj.manager.msg.dao;

import com.cdkj.common.base.model.dao.BaseMapper;
import com.cdkj.model.msg.pojo.MsgIdentifyCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MsgIdentifyCodeMapper extends BaseMapper<MsgIdentifyCode, String> {

    /**
     * 通过手机号码查询验证码信息，只限一个
     *
     * @param sysAccount 帐套号
     * @param mobile     手机号码
     * @return 验证码信息
     */
    MsgIdentifyCode selectByMobile(@Param("sysAccount") String sysAccount, @Param("mobile") String mobile);

    /**
     * 通过手机号码查询验证码信息
     *
     * @param sysAccount 帐套号
     * @param mobile     手机号码
     * @return 验证码信息
     */
    List<MsgIdentifyCode> listByMobile(@Param("sysAccount") String sysAccount, @Param("mobile") String mobile);
}