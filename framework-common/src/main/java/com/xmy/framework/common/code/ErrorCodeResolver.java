package com.xmy.framework.common.code;

import com.xmy.framework.common.config.ServiceProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Locale;

/**
 * 错误码解析器：把 {@link ErrorCode} 的数字编码拼接为「服务名缩写.数字编码」的完整错误码。
 */
@RequiredArgsConstructor
public class ErrorCodeResolver {

    private static final String SEPARATOR = ".";

    private final ServiceProperties properties;

    public String resolve(ErrorCode errorCode) {
        String prefix = StringUtils.hasText(errorCode.getPrefix())
                ? errorCode.getPrefix().trim().toUpperCase(Locale.ROOT)
                : servicePrefix();
        return prefix + SEPARATOR + errorCode.getCode();
    }

    private String servicePrefix() {
        return properties.getAbbr().trim().toUpperCase(Locale.ROOT);
    }


    @PostConstruct
    void check() {
        if (CommonErrorCode.PREFIX.equalsIgnoreCase(properties.getAbbr().trim())) {
            throw new IllegalStateException("xmy.service.abbr 不能使用保留前缀 " + CommonErrorCode.PREFIX);
        }
    }
}
