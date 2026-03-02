package com.smwu_itple.backend.pay.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoReadyRequest {
    private String orderId;
    private String userId;
    private String itemName;
    private int totalAmount;
    private int vatAmount;
}

