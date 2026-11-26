package com.xmy.framework.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 框架内置的通用错误码。
 *
 * <p>编码规则：0 为成功码（不参与服务名前缀拼接），01xx 用于登录认证，04xx 对应客户端错误，
 * 05xx 对应服务端错误。业务项目可在此规范内继续扩展自己的数字编码。
 */
@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    /** 成功。成功码在响应体中固定为 0，不拼接服务名前缀 */
    SUCCESS("0", "成功"),

    /** 请求参数错误 */
    BAD_REQUEST("0400", "请求参数错误"),

    /** 未认证或登录态失效 */
    UNAUTHORIZED("0401", "未认证或登录已过期"),

    /** 无访问权限 */
    FORBIDDEN("0403", "无访问权限"),

    /** 请求资源不存在 */
    NOT_FOUND("0404", "请求资源不存在"),

    /** 请求方法不支持 */
    METHOD_NOT_ALLOWED("0405", "请求方法不支持"),

    /** 参数校验失败 */
    PARAM_VALIDATION_FAILED("0422", "参数校验失败"),

    /** 系统内部错误 */
    INTERNAL_ERROR("0500", "系统内部错误"),

    /** 服务暂不可用 */
    SERVICE_UNAVAILABLE("0503", "服务暂不可用");

    private final String code;

    private final String message;

    public static final String PREFIX = "COMMON";

    @Override
    public String getPrefix() {
        return PREFIX;
    }
}
