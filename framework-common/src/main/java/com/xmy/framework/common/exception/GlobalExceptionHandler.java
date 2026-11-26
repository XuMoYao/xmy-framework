package com.xmy.framework.common.exception;

import com.xmy.framework.common.code.CommonErrorCode;
import com.xmy.framework.common.code.ErrorCodeResolver;
import com.xmy.framework.common.model.Response;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;

/**
 * 全局异常处理：把各类异常统一转换为 {@link Response}，并输出符合规范的错误码。
 *
 * <p>错误码统一为「服务名缩写.数字编码」，服务名缩写来自配置 {@code xmy.service.abbr}。
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ErrorCodeResolver errorCodeResolver;

    /**
     * 业务异常
     */
    @ExceptionHandler(BizException.class)
    public Response<Void> handleBizException(BizException e) {
        String code = errorCodeResolver.resolve(e.getErrorCode());
        log.warn("业务异常: code={}, message={}", code, e.getMessage());
        return Response.fail(code, e.getMessage());
    }

    /**
     * 参数绑定失败，覆盖 {@code @RequestBody} 的校验失败与表单/查询参数绑定失败
     */
    @ExceptionHandler(BindException.class)
    public Response<Void> handleBindException(BindException e) {
        String detail = e.getBindingResult().getFieldErrors().stream()
                .map(this::formatFieldError)
                .collect(Collectors.joining("; "));
        return validationFailure(detail);
    }

    /**
     * 方法参数校验失败（{@code @Validated} 标注在类上的场景）
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Response<Void> handleConstraintViolationException(ConstraintViolationException e) {
        String detail = e.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining("; "));
        return validationFailure(detail);
    }

    /**
     * 控制器方法参数校验失败（Spring Framework 6.1+ 默认行为）
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public Response<Void> handleHandlerMethodValidationException(HandlerMethodValidationException e) {
        String detail = e.getAllErrors().stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .filter(StringUtils::hasText)
                .collect(Collectors.joining("; "));
        return validationFailure(detail);
    }

    /**
     * 请求方法不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response<Void> handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        String code = errorCodeResolver.resolve(CommonErrorCode.METHOD_NOT_ALLOWED);
        log.warn("请求方法不支持: {}", e.getMethod());
        return Response.fail(code, CommonErrorCode.METHOD_NOT_ALLOWED.getMessage());
    }

    /**
     * 静态资源/接口路径不存在
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public Response<Void> handleNoResourceFound(NoResourceFoundException e) {
        String code = errorCodeResolver.resolve(CommonErrorCode.NOT_FOUND);
        log.warn("请求资源不存在: {}", e.getResourcePath());
        return Response.fail(code, CommonErrorCode.NOT_FOUND.getMessage());
    }

    /**
     * 未预期异常，对外不暴露内部细节
     */
    @ExceptionHandler(Exception.class)
    public Response<Void> handleException(Exception e) {
        String code = errorCodeResolver.resolve(CommonErrorCode.INTERNAL_ERROR);
        log.error("未预期异常: code={}", code, e);
        return Response.fail(code, CommonErrorCode.INTERNAL_ERROR.getMessage());
    }

    private Response<Void> validationFailure(String detail) {
        String code = errorCodeResolver.resolve(CommonErrorCode.PARAM_VALIDATION_FAILED);
        String message = StringUtils.hasText(detail) ? detail : CommonErrorCode.PARAM_VALIDATION_FAILED.getMessage();
        log.warn("参数校验失败: {}", message);
        return Response.fail(code, message);
    }

    private String formatFieldError(FieldError error) {
        return error.getField() + ": " + error.getDefaultMessage();
    }
}
