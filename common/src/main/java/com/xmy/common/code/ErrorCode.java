package com.xmy.common.code;

/**
 * 错误码规范：最终对外呈现为「服务名缩写 + 英文点 + 数字编码」，例如 USER.0001。
 *
 * <p>这里的 {@link #getCode()} 只返回数字编码部分，服务名缩写由 {@link ErrorCodeResolver}
 * 在运行时从配置 {@code xmy.service.abbr} 读取并拼接，因此业务项目无需也不应把服务名写进错误码。
 *
 * <p>业务项目扩展方式：定义自己的枚举实现本接口即可，例如
 * <pre>{@code
 * @Getter
 * @RequiredArgsConstructor
 * public enum UserErrorCode implements ErrorCode {
 *     USER_NOT_FOUND("0001", "用户不存在");
 *
 *     private final String code;
 *     private final String message;
 * }
 * }</pre>
 */
public interface ErrorCode {

    /** 纯数字编号，如 0001 */
    String getCode();

    String getMessage();

    /** 固定前缀；返回 null 表示使用当前服务配置的缩写 */
    default String getPrefix() {
        return null;
    }
}
