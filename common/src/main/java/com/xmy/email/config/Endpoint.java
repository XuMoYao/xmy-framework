package com.xmy.email.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Endpoint {
    // 应该是发送区域，国内邮件目前看只有杭州可选
    HANGZHOU("dm.aliyuncs.com");

    private final String value;
}
