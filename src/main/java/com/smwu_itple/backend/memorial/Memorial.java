package com.smwu_itple.backend.memorial;

import com.smwu_itple.backend.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "memorial")
public class Memorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memorial_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @ManyToOne
    @JoinColumn(name = "memorial_user", nullable = true)
    private User memorialUser; // 신청자

    @Lob
    private byte[] image;

    @Column(nullable = false)
    private String mask;

    @Column(columnDefinition = "LONGTEXT")
    private String content;
}
