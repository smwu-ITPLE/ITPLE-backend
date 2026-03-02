package com.smwu_itple.backend.pay.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaySumResponse {
    private String ownerName;
    private Integer totalAmount;
    private Double percentage;
}
