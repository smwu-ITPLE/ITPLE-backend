package com.smwu_itple.backend.chat;

import com.smwu_itple.backend.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    @ManyToOne
    @JoinColumn(name="chat_from_id", nullable = false)
    private User fromUser;

    @ManyToOne
    @JoinColumn(name="chat_to_id", nullable = false)
    private User toUser;

    @Column(nullable = false)
    private String chatSubject; //마지막으로 전송한 메시지

    @Column(nullable = false)
    private LocalDateTime chatTime; //마지막으로 전송한 시간

}
