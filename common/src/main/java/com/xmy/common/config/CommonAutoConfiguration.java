package com.xmy.common.config;

import com.xmy.common.code.ErrorCodeResolver;
import com.xmy.common.exception.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 通用能力自动装配：统一响应体相关的错误码解析与全局异常处理。
 */
@AutoConfiguration
@EnableConfigurationProperties(ServiceProperties.class)
public class CommonAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ErrorCodeResolver errorCodeResolver(ServiceProperties properties) {
        return new ErrorCodeResolver(properties);
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    @ConditionalOnClass(name = "org.springframework.web.servlet.DispatcherServlet")
    static class WebExceptionHandlerConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public GlobalExceptionHandler globalExceptionHandler(ErrorCodeResolver errorCodeResolver) {
            return new GlobalExceptionHandler(errorCodeResolver);
        }
    }
}
