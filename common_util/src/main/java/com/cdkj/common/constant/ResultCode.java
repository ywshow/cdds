package com.cdkj.common.constant;

/**
 * description: 结果代码<br>
 * date: 2016/7/7 14:15
 *
 * @author bovine
 * @version 1.0
 * @since JDK 1.8
 */
public class ResultCode {
    /**
     * 用户操作异常
     **/
    public static final int OPERATE_ERROR = -3;
    /**
     * 应用程序异常
     **/
    public static final int ERROR = -2;
    /**
     * 参数为空
     **/
    public static final int PARAMETER_IS_NULL = -4;
    /**
     * 操作失败
     **/
    public static final int FAIL = -1;
    /**
     * 操作成功
     **/
    public static final int SUCCESS = 1;
    /**
     * 查询列表操作成功，但是没有数据
     **/
    public static final int NO_DATA = -5;
    /**
     * 增加修改时，数据已经存在
     **/
    public static final int DATA_HAVA = 3;
}
