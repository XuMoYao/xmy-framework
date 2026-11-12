package com.xmy.email;

import com.aliyun.dm20151123.models.SingleSendMailRequest;
import com.xmy.email.config.EmailProperties;
import com.xmy.email.model.SendResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class EmailServiceTest {
    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailProperties properties;

    @Test
    void singleSend_success() {
        com.aliyun.dm20151123.models.SingleSendMailRequest singleSendMailRequest = new com.aliyun.dm20151123.models.SingleSendMailRequest();
        singleSendMailRequest.setAccountName("xmy@xumoyao.space");
        singleSendMailRequest.setAddressType(1);
        singleSendMailRequest.setReplyToAddress(true);
        singleSendMailRequest.setToAddress("xumoyaohang@outlook.com");
        singleSendMailRequest.setTextBody("代码发送测试");
        //singleSendMailRequest.setSubject("来自");

        SingleSendMailRequest.SingleSendMailRequestTemplate template = new SingleSendMailRequest.SingleSendMailRequestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("Name", "gagag");
        template.setTemplateData(params);
        template.setTemplateId("446772");

        singleSendMailRequest.setTemplate(template);
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();

        SendResult result = emailService.singleSend(singleSendMailRequest, runtime);
        System.out.println(result);
    }

}