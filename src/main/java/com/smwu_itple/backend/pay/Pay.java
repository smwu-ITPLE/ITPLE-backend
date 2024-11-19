package com.smwu_itple.backend.pay;

import com.smwu_itple.backend.chat.Chat;
import com.smwu_itple.backend.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "pay")
public class Pay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_id")
    private Long id;

    @Column(nullable = false)
    private int money;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @Column(nullable = false)
    private String envelope;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
