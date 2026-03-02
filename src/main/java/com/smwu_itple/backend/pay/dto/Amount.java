package com.smwu_itple.backend.pay.dto;

import lombok.Data;

@Data
public class Amount {
    private int total;
    private int tax_free;
    private int tax;
    private int point;
    private int discount;
    private int green_deposit;
}