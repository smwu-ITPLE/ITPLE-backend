package com.smwu_itple.backend.memorial;

import com.smwu_itple.backend.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Memorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memorialId;

    @Column(nullable = false, length = 50)
    private String memorialTitle; // 방제목

    @ManyToOne
    @JoinColumn(name = "memorial_user", nullable = false)
    private User memorialUser; // 신청자

    @Lob
    private byte[] memorialImage; // 이미지

    @Column(nullable = false)
    private int memorialMask; // 1/2/3 중에서 선택

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String memorialContent; // 관련 문구
}
