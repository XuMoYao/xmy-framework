package com.xmy.email.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(EmailProperties.class)
public class EmailConfig {

    @Bean
    public com.aliyun.dm20151123.Client emailClient(EmailProperties properties) throws Exception {
        // 使用默认凭据链（环境变量、配置文件、ECS 角色等），代码里不出现 AK
        com.aliyun.credentials.Client credential = new com.aliyun.credentials.Client();

        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                .setCredential(credential);
        config.setEndpoint(properties.getEndpoint().getValue());

        return new com.aliyun.dm20151123.Client(config);
    }
}
