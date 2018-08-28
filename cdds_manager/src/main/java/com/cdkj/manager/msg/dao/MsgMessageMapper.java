package com.cdkj.manager.msg.dao;

import com.cdkj.common.base.model.dao.BaseMapper;
import com.cdkj.model.msg.pojo.MsgMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MsgMessageMapper extends BaseMapper<MsgMessage, String> {

    /**
     * description: 根据用户ID和是否已读查询 <br>
     *
     * @param userId  用户ID
     * @param isRead  是否已读【0：未读 1：已读】(可空)
     * @param msgType 消息类型【0：站内信 1：短信】(可空)
     * @return 返回查询列表
     */
    List<MsgMessage> selectByUserId(@Param(value = "userId") String userId, @Param(value = "isRead") Integer isRead, @Param(value = "msgType") String msgType);

    /**
     * description: 更改是否已读状态 <br>
     *
     * @param userId 用户ID
     * @param isRead 是否已读【0：未读 1：已读】
     * @return 操作数量
     */
    int updateListByUserIdAndIsRead(@Param(value = "userId") String userId, @Param(value = "isRead") Integer isRead);

}