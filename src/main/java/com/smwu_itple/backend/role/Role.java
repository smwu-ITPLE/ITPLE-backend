package com.smwu_itple.backend.role;

import com.smwu_itple.backend.late.Late;
import com.smwu_itple.backend.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @ManyToOne
    @JoinColumn(name = "role_user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_late_id", nullable = false)
    private Late late;

    @Column(nullable = false)
    private boolean role; // 0(owner), 1(guest)

    private String roleRelation; // role이 0이면 관계 설정

}
