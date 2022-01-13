package com.viwcy.domain.common;

/**
 * TODO //
 *
 * <p> Title: BaseController </p>
 * <p> Description: BaseController </p>
 * <p> History: 2020/9/4 23:02 </p>
 * <pre>
 *      Copyright: Create by FQ, ltd. Copyright(©) 2020.
 * </pre>
 * Author  FQ
 * Version 0.0.1.RELEASE
 */
public class BaseController {

    public BaseController() {
    }

    public final ResultEntity success() {
        return new ResultEntity(ResultCode.RESULT_SUCCESS.getCode(), ResultCode.RESULT_SUCCESS.getMessage(), null);
    }

    public final <T> ResultEntity<T> success(T data) {
        return new ResultEntity(ResultCode.RESULT_SUCCESS.getCode(), ResultCode.RESULT_SUCCESS.getMessage(), data);
    }

    public final <T> ResultEntity<T> success(String message) {
        return new ResultEntity(ResultCode.RESULT_SUCCESS.getCode(), message, null);
    }

    public final <T> ResultEntity<T> success(String message, T data) {
        return new ResultEntity(ResultCode.RESULT_SUCCESS.getCode(), message, data);
    }

    public final <T> ResultEntity<T> fail() {
        return new ResultEntity(ResultCode.RESULT_FAIL.getCode(), ResultCode.RESULT_FAIL.getMessage(), null);
    }

    public final <T> ResultEntity<T> fail(String message) {
        return new ResultEntity(ResultCode.RESULT_FAIL.getCode(), message, null);
    }

    public final <T> ResultEntity<T> hint(String message) {
        return new ResultEntity(ResultCode.RESULT_HINT.getCode(), message, null);
    }

    public final <T> ResultEntity<T> error(String message) {
        return new ResultEntity(ResultCode.RESULT_ERROR.getCode(), message, null);
    }

    public final <T> ResultEntity<T> warning(String message) {
        return new ResultEntity(ResultCode.RESULT_WARNING.getCode(), message, null);
    }

}
