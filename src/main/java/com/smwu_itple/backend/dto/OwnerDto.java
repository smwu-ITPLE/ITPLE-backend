package com.smwu_itple.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OwnerDto {
    private String name; // 상주 이름

    private String relation; // 관계

    private String phoneNumber; // 전화번호
}
