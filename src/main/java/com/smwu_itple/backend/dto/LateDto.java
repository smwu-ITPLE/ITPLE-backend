package com.smwu_itple.backend.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class LateDto {

    private String name;
    private byte[] profile;
    private int age; // 나이는 0 이상이어야 함
    private boolean gender; // true: male, false: female
    private LocalDateTime datePass; // 별세일 날짜
    private LocalDateTime dateDeath; // 발인 날짜
    private String location;
    private String content;
}