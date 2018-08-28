package com.cdkj.manager.wechat.dao;

import com.cdkj.common.base.model.dao.BaseMapper;
import com.cdkj.model.wechat.pojo.WechatFuncInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface WechatFuncInfoMapper extends BaseMapper<WechatFuncInfo, String> {
    /**
     * 根据微信主信息ID进行查询
     *
     * @param mainId WechatMainInfo.ID
     * @return 授权信息
     */
    List<WechatFuncInfo> selectByMainId(@Param("mainId") String mainId);

    /**
     * 根据微信主信息ID进行删除
     *
     * @param mainId WechatMainInfo.ID
     * @return 删除影响行数
     */
    int deleteByMainId(@Param("mainId") String mainId);
}