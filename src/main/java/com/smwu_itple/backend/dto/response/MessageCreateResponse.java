package com.smwu_itple.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MessageCreateResponse {
    private String senderName;
    private String receiverName;
    private String content;
    private String attachment;
    private LocalDateTime createdAt;
}
