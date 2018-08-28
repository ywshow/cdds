package com.cdkj.common.constant;

/**
 * Created by USER on 2016/2/23.
 */
public class Constant {
    public static final String Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";
    public static final String Y_M_D = "yyyy-MM-dd";
    public static final String CHARSET_UTF8 = "utf-8";

    /**
     * 启用
     */
    public static short ENABLED_Y = 1;
    /**
     * 不启用
     */
    public static short ENABLED_N = 0;

    /**
     * 默认status状态
     */
    public static short STATUS_DEFAULT = 1;

    /**
     * 默认version状态
     */
    public static int VERSION_DEFAULT = 1;

    /**
     * 顶级组织 （父id为001的组织）
     */
    public static String TREE_ROOT_ID = "001";

    public static String GLOBAL_SYSACCOUNT="ALL";

    /**
     * 定时任务状态
     *
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL((short)0),
        /**
         * 暂停
         */
        PAUSE((short)1);

        private Short value;

        private ScheduleStatus(Short value) {
            this.value = value;
        }

        public Short getValue() {
            return value;
        }
    }
}
