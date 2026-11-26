package com.xmy.framework.common.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "xmy.service")
public class ServiceProperties {

    /**
     * 服务名缩写，作为错误码前缀。业务服务必须配置，不区分大小写。
     */
    @NotBlank(message = "必须配置 xmy.service.（服务名缩写），例如 USER")
    private String abbr;
}
