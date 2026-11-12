package com.xmy.email.model;

import com.aliyun.dm20151123.models.SingleSendMailResponse;
import com.aliyun.tea.TeaException;
import lombok.Data;

@Data
public class SendResult {
    private boolean success;

    private String EnvId;

    private String RequestId;

    private String message;

    private Object recommend;

    public static SendResult success(SingleSendMailResponse response) {
        SendResult result = new SendResult();
        result.setSuccess(true);
        result.setEnvId(response.getBody().getEnvId());
        result.setRequestId(response.getBody().getRequestId());

        return result;
    }


    public static SendResult fail(TeaException e, String recommend) {
        SendResult result = fail(e);
        result.setRecommend(recommend);

        return result;
    }


    public static SendResult fail(Exception e) {
        SendResult result = new SendResult();
        result.setSuccess(false);
        result.setMessage(e.getMessage());

        return result;
    }
}
