package com.smwu_itple.backend.pay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class KakaoApproveRequest {
    private String orderId;
    private String userId;
    private String pgToken;
}
