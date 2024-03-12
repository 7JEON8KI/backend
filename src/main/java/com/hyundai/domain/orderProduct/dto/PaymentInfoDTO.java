package com.hyundai.domain.orderProduct.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * author : 이소민
 */

@Getter
public class PaymentInfoDTO {
    private String quotaInfo;
    private String merchantUid;
    private String payMethod;
    private int payPrice;

    @Builder
    public PaymentInfoDTO(String quotaInfo, String merchantUid, String payMethod, int payPrice) {
        this.quotaInfo = quotaInfo;
        this.merchantUid = merchantUid;
        this.payMethod = payMethod;
        this.payPrice = payPrice;
    }
}
