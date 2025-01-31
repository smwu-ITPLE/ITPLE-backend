package com.smwu_itple.backend.dto.request;

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
