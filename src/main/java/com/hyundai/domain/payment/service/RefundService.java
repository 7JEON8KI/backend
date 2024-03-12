package com.hyundai.domain.payment.service;

import java.io.IOException;

/**
 * author : 이소민
 */

public interface RefundService {
    public void refundRequest(String access_token, String merchant_uid, String reason) throws IOException;
    public String getToken(String apiKey, String secretKey) throws Exception;

}
