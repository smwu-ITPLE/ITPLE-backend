package com.smwu_itple.backend.pay;

import com.smwu_itple.backend.chat.Chat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Pay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payId;

    @OneToOne
    @JoinColumn(name = "pay_chat_id", nullable = false)
    private Chat chat;

    @Column(nullable = false)
    private boolean payEnvelope; // 0(검정), 1(회색)

    private int payMoney; // 송금액
}
