package com.smwu_itple.backend.late.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LateCreateResponse {
    private Long id;
    private String name;
    private String profile; // 파일 경로
}
