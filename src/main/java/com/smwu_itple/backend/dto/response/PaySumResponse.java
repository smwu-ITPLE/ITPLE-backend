package com.smwu_itple.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaySumResponse {
    private String ownerName;
    private Integer totalAmount;
    private Double percentage;
}
