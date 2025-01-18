package com.smwu_itple.backend.domain;

import com.smwu_itple.backend.domain.Late;
import com.smwu_itple.backend.domain.Message;
import com.smwu_itple.backend.domain.Pay;
import com.smwu_itple.backend.domain.User;
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
    private List<Pay> pays;
}
