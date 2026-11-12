package com.xmy.email;

import com.aliyun.dm20151123.models.SingleSendMailRequest;
import com.aliyun.dm20151123.models.SingleSendMailResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.models.RuntimeOptions;
import com.xmy.email.config.EmailProperties;
import com.xmy.email.model.SendResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final com.aliyun.dm20151123.Client client;

    private final EmailProperties properties;

    public SendResult singleSend(SingleSendMailRequest request, RuntimeOptions options) {
        try {
            request.setAccountName(properties.getAccountName());
            SingleSendMailResponse response = client.singleSendMailWithOptions(request, options);
            return SendResult.success(response);
        } catch (TeaException e) {
            Map<String, Object> data = e.getData();
            String recommend = data != null ? String.valueOf(data.get("Recommend")) : null;
            log.error("邮件发送失败: {}, 诊断地址: {}", e.getMessage(), recommend, e);
            return SendResult.fail(e, recommend);
        } catch (Exception e) {
            log.error("邮件发送异常", e);
            return SendResult.fail(e);
        }
    }


}
