package com.smwu_itple.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // 모든 필드를 받는 생성자 자동 생성
@NoArgsConstructor // 기본 생성자 자동 생성
public class ArchieveDTO {
    private Long id;
    private String content;
    private String color;
}