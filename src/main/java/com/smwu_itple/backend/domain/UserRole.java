package com.smwu_itple.backend.domain;

import com.smwu_itple.backend.domain.Late;
import com.smwu_itple.backend.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    private String roleRelation;

    @Enumerated(EnumType.STRING) // EnumType.STRING: enum 값을 문자열로 저장
    @Column(nullable = false)
    private Authority authority;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "late_id")
    private Late late;

    // 권한 enum 정의
    public enum Authority {
        OWNER, GUEST;
    }
}
