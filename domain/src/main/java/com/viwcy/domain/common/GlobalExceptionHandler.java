package com.viwcy.domain.common;

import org.apache.ibatis.javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO //、、在Controller层进行异常拦截和包装，给前端返回
 *
 * <p> Title: GlobalExceptionHandler </p >
 * <p> Description: GlobalExceptionHandler </p >
 * <p> History: 2020/9/27 14:35 </p >
 * <pre>
 *      Copyright (c) 2020 FQ (fuqiangvn@163.com) , ltd.
 * </pre>
 * Author  FQ
 * Version 0.0.1.RELEASE
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private HttpServletRequest request;

    @Autowired
    public GlobalExceptionHandler(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Exception异常封装
     */
    @ExceptionHandler(value = Exception.class)
    public ResultEntity exceptionHandler(Exception e) {
        log.error("Error path: " + request.getRequestURL());
        log.error("Global catch exception: ", e);
        return ResultEntity.fail("服务器异常");
    }

    /**
     * NotFoundException(404)异常封装
     */
    @ExceptionHandler(value = NotFoundException.class)
    public ResultEntity exceptionHandler(NotFoundException e) {
        log.error("Error path: " + request.getRequestURL());
        log.error("Global catch exception: ", e);
        return ResultEntity.fail(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    /**
     * HttpRequestMethodNotSupportedException异常封装(请求方式不支持)
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResultEntity exceptionHandler(HttpRequestMethodNotSupportedException e) {
        log.error("Error path: " + request.getRequestURL());
        log.error("Global catch exception: ", e);
        return ResultEntity.fail(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
    }

    /**
     * HttpMediaTypeNotSupportedException异常封装(主要针对于application/json入参格式错误，如JSON body传成text或html)
     */
    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public ResultEntity exceptionHandler(HttpMediaTypeNotSupportedException e) {
        log.error("Error path: " + request.getRequestURL());
        log.error("Global catch exception: ", e);
//        return ResultEntity.fail(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), e.getMessage());
        return ResultEntity.fail("参数非法，请检查传入数据格式");
    }

    /**
     * 自定义的业务异常
     */
    @ExceptionHandler(value = BusinessException.class)
    public ResultEntity exceptionHandler(BusinessException e) {
        log.error("Error path: " + request.getRequestURL());
        log.error("Global catch exception: ", e);
        return ResultEntity.fail(e.getCode(), e.getMessage());
    }

    /**
     * 空指针异常
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ResultEntity exceptionHandler(NullPointerException e) {
        log.error("Error path: " + request.getRequestURL());
        log.error("Global catch exception: ", e);
        return ResultEntity.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "NullPointerException");
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResultEntity exceptionHandler(MissingServletRequestParameterException e) {
        log.error("Error path: " + request.getRequestURL());
        log.error("Global catch exception: ", e);
        return ResultEntity.fail(String.format("%s is not blank", e.getParameterName()));
    }

    @ExceptionHandler(value = BindException.class)
    public ResultEntity exceptionHandler(BindException e) {
        log.error("Error path: " + request.getRequestURL());
        log.error("Global catch exception: ", e);
        return ResultEntity.fail(String.format("%s is not blank", e.getFieldError().getField()));
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResultEntity exceptionHandler(HttpMessageNotReadableException e) {
        log.error("Error path: " + request.getRequestURL());
        log.error("Global catch exception: ", e);
        return ResultEntity.fail("参数非法，请检查传入数据格式");
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultEntity exceptionHandler(MethodArgumentNotValidException e) {
        log.error("Error path: " + request.getRequestURL());
        log.error("Global catch exception: ", e);
        FieldError fieldError = e.getBindingResult().getFieldError();
        String message = fieldError != null ? String.format("%s%s", fieldError.getField(), fieldError.getDefaultMessage()) : e.getMessage();
        return ResultEntity.fail(message);
    }

}
