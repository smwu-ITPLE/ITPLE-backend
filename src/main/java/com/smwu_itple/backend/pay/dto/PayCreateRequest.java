package com.smwu_itple.backend.pay.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PayCreateRequest {
    private Long receiverId;
    private Integer envelope;
    private Integer amount;
}
