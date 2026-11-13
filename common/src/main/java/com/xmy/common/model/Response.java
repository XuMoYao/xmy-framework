package com.xmy.common.model;

import lombok.Data;

/**
 * 全局统一响应体，所有接口（成功与失败）统一返回该结构。
 *
 * @param <T> 业务数据类型
 */
@Data
public class Response<T> {

    /**
     * 成功码。成功码不拼接服务名前缀，失败码遵循「服务名缩写.数字编码」规范。
     */
    public static final String SUCCESS_CODE = "0";

    private static final String SUCCESS_MESSAGE = "成功";

    /**
     * 响应码，成功为 0，失败为「服务名缩写.数字编码」，例如 USER.0001
     */
    private String code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 业务数据
     */
    private T data;

    public static <T> Response<T> success() {
        return success(null);
    }

    public static <T> Response<T> success(T data) {
        return of(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    public static <T> Response<T> success(String message, T data) {
        return of(SUCCESS_CODE, message, data);
    }

    public static <T> Response<T> fail(String code, String message) {
        return of(code, message, null);
    }

    public static <T> Response<T> of(String code, String message, T data) {
        Response<T> response = new Response<>();
        response.setCode(code);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
}
