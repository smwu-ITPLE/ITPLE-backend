package com.smwu_itple.backend.pay.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PayCreateResponse {
    private String senderName;
    private String receiverName;
    private Integer envelope;
    private Integer amount;
    private LocalDateTime createdAt;
}
