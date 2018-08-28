/**
 * project name:cdds
 * file name:ExceptionHandle
 * package name:com.cdkj.asset.common.exception
 * date:2018/3/17 上午10:34
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.common.exception;

import com.cdkj.common.constant.ResultCode;
import com.cdkj.util.Result;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * description: 全局异常处理 <br>
 * date: 2018/3/17 上午10:34
 *
 * @author bovine
 * @version 1.0
 * @since JDK 1.8
 */
@RestControllerAdvice
public class ExceptionHandle {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        if (e instanceof CustException) {
            CustException courseException = (CustException) e;
            logger.error("系统异常CustException>>>", e);
            return Result.error(courseException.getErrCode(), courseException.getMessage());
        }else if(e instanceof AuthorizationException){
            logger.error("权限异常>>>", e);
            return Result.error(ResultCode.FAIL,"无访问权限");
        }
        logger.error("系统异常>>>", e);
        return Result.error(ResultCode.FAIL, e.getMessage());
    }
}