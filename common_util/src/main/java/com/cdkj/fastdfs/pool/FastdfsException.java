package com.cdkj.fastdfs.pool;

/**
 * @description: 文件服务器异常类
 */
public class FastdfsException extends RuntimeException {
    private static final long serialVersionUID = -2460220772499734461L;

    public FastdfsException() {
        super();
    }

    public FastdfsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public FastdfsException(String message, Throwable cause) {
        super(message, cause);
    }

    public FastdfsException(String message) {
        super(message);
    }

    public FastdfsException(Throwable cause) {
        super(cause);
    }


}
