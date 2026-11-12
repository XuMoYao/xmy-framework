package com.xmy.email.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailMessage {
    /**
     * 收件人，多个用逗号分隔（阿里云限制最多 100 个）
     */
    private String to;

    private String subject;

    /**
     * 纯文本正文
     */
    private String textBody;

    /**
     * HTML 正文，与 textBody 二选一
     */
    private String htmlBody;
}
