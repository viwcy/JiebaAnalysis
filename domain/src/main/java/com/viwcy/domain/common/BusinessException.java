package com.viwcy.domain.common;

/**
 * TODO //业务异常，对外抛出，实行全局捕获
 *
 * <p> Title: BusinessException </p >
 * <p> Description: BusinessException </p >
 * <p> History: 2020/9/16 10:22 </p >
 * <pre>
 *      Copyright (c) 2020 FQ (fuqiangvn@163.com) , ltd.
 * </pre>
 * Author  FQ
 * Version 0.0.1.RELEASE
 */
public class BusinessException extends BaseException {

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Integer code, String message) {
        super(code, message);
    }
}
