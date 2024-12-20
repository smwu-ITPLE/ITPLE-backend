package com.smwu_itple.backend.chat;

import com.smwu_itple.backend.late.Late;
import com.smwu_itple.backend.message.Message;
import com.smwu_itple.backend.pay.Pay;
import com.smwu_itple.backend.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@Table(name = "chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "late_id")
    private Late late;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messages;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Pay> payments;
}
