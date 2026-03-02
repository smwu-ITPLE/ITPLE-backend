package com.smwu_itple.backend.pay.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoApproveRequest {
    private String orderId;
    private String userId;
    private String pgToken;
}
