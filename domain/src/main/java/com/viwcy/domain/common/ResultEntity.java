package com.viwcy.domain.common;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.time.FastDateFormat;

import java.io.Serializable;

/**
 * TODO //
 *
 * <p> Title: ResultEntity </p>
 * <p> Description: ResultEntity </p>
 * <p> History: 2020/9/4 23:02 </p>
 * <pre>
 *      Copyright: Create by FQ, ltd. Copyright(©) 2020.
 * </pre>
 * Author  FQ
 * Version 0.0.1.RELEASE
 */
@Data
@ToString
@EqualsAndHashCode
public final class ResultEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final FastDateFormat FAST_DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    private long timestamp = System.currentTimeMillis();
    private String date = FAST_DATE_FORMAT.format(timestamp);
    private int code;
    private String message;
    private T data;

    public ResultEntity() {
    }

    public ResultEntity(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResultEntity<T> success() {
        return new ResultEntity(ResultCode.RESULT_SUCCESS.getCode(), ResultCode.RESULT_SUCCESS.getMessage(), null);
    }

    public static <T> ResultEntity<T> success(T data) {
        return new ResultEntity(ResultCode.RESULT_SUCCESS.getCode(), ResultCode.RESULT_SUCCESS.getMessage(), data);
    }

    public static <T> ResultEntity<T> success(String message) {
        return new ResultEntity(ResultCode.RESULT_SUCCESS.getCode(), message, null);
    }

    public static <T> ResultEntity<T> success(String message, T data) {
        return new ResultEntity(ResultCode.RESULT_SUCCESS.getCode(), message, data);
    }

    public static <T> ResultEntity<T> fail(String message) {
        return new ResultEntity(ResultCode.RESULT_FAIL.getCode(), message, null);
    }

    public static <T> ResultEntity<T> fail(int code, String message) {
        return new ResultEntity(code, message, null);
    }

    public static <T> ResultEntity<T> hint(String message) {
        return new ResultEntity(ResultCode.RESULT_HINT.getCode(), message, null);
    }

    public static <T> ResultEntity<T> error(String message) {
        return new ResultEntity(ResultCode.RESULT_ERROR.getCode(), message, null);
    }

    public static <T> ResultEntity<T> warning(String message) {
        return new ResultEntity(ResultCode.RESULT_WARNING.getCode(), message, null);
    }
}
