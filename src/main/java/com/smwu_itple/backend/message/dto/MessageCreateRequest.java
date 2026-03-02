package com.smwu_itple.backend.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class MessageCreateRequest {
    private Long receiverId;
    private String content;
}
