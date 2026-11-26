package com.xmy.framework.common.exception;

import com.xmy.framework.common.code.ErrorCode;
import lombok.Getter;

/**
 * 业务异常。抛出后用 {@link GlobalExceptionHandler} 统一转换为 {@code Response}，
 * 错误码由 {@link ErrorCode} 与配置的服务名缩写组合而成。
 */
@Getter
public class BizException extends RuntimeException {

    private final transient ErrorCode errorCode;

    public BizException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    /**
     * 使用自定义提示信息覆盖错误码的默认信息。
     */
    public BizException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BizException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public BizException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
