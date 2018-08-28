package com.cdkj.common.exception;

import com.cdkj.util.JsonUtils;
import com.cdkj.util.ResultUtil;

/**
 * Created by Arvin on 2016-06-24.
 */
public class CustException extends RuntimeException {
    public CustException(String message) {
        super(message);
        this.msg = message;
    }

    private int errCode;
    private int errCode2;
    private String errorCode3;
    private String msg;

    public CustException(int errCode, String message) {
        super(message);
        this.errCode = errCode;
        this.msg = message;
    }

    public CustException(int errCode, int errCode2, String errorCode3, String message) {
        super(message);
        this.errCode = errCode;
        this.errCode2 = errCode2;
        this.errorCode3 = errorCode3;
        this.msg = message;
    }

    public CustException(Throwable cause) {
        super(cause);
    }

    public CustException(String message, Throwable cause) {
        super(message, cause);
        this.msg = message;
    }

    public String getJsonError() {
        return JsonUtils.toJson(ResultUtil.getResult(errCode, msg));
    }

    public int getErrCode() {
        return this.errCode;
    }

    public String getMsg() {
        if (errCode == 0)
            return msg;
        else if (errCode2 == 0) {
            return errCode + ":" + msg;
        }
        return errCode + "-" + errCode2 + "-" + errorCode3 + ":" + msg;
    }
}