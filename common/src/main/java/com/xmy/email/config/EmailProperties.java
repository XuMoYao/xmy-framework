package com.xmy.email.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "aliyun.email")
public class EmailProperties {
    private Endpoint endpoint = Endpoint.HANGZHOU;

    /** 发信地址，对应 setAccountName，例如 xmy@xumoyao.space */
    private String accountName;
}
