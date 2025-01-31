package com.smwu_itple.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "owner")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String name; // 상주 이름

    @Column(nullable = false, length = 10)
    private String relation; // 관계

    @Column(nullable = false, length = 15)
    private String phoneNumber; // 전화번호

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Owner와 User 연결
    private User user;

    @ManyToOne
    @JoinColumn(name = "late_id") // 외래 키 설정
    private Late late;

    @OneToMany(mappedBy = "receiver")
    private List<Message> messages;
}