package com.smwu_itple.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PayCreateResponse {
    private Long lateId;
    private String senderName;
    private String receiverName;
    private Integer envelope;
    private Integer amount;
    private LocalDateTime createdAt;
}
