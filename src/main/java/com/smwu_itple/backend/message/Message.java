package com.smwu_itple.backend.message;

import com.smwu_itple.backend.chat.Chat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "message_chat_id", nullable = false)
    private Chat chat;

    @Column(nullable = false)
    private boolean isFromSender; // 0(조문), 1(답장)

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String messageSubject; // 메시지 내용

    @Column(nullable = false)
    private LocalDateTime messageTime;
}
