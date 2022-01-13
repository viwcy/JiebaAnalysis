package com.viwcy.domain.common;

/**
 * TODO //、、Controller层响应状态码
 *
 * <p> Title: ResultCode </p>
 * <p> Description: ResultCode </p>
 * <p> History: 2020/9/4 23:02 </p>
 * <pre>
 *      Copyright: Create by FQ, ltd. Copyright(©) 2020.
 * </pre>
 * Author  FQ
 * Version 0.0.1.RELEASE
 */
public enum ResultCode {

    // 成功
    RESULT_SUCCESS(200, "SUCCESS"),
    // 失败
    RESULT_FAIL(20000, "FAIL"),
    // 提示
    RESULT_HINT(20001, "PROMPT"),
    // 警告
    RESULT_WARNING(20002, "WARNING"),
    // 错误
    RESULT_ERROR(20500, "ERROR");

    private int code;
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
