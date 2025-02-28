package com.smwu_itple.backend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoApproveRequest {
    private String orderId;
    private String userId;
    private String pgToken;
}
