package com.smwu_itple.backend.dto.request;

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

