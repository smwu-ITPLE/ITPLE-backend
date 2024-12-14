package com.smwu_itple.backend.late;

import com.smwu_itple.backend.gallery.Gallery;
import com.smwu_itple.backend.user.User;
import com.smwu_itple.backend.userrole.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "late")
public class Late {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "late_id")
    private Long id;

    @Column(nullable = false, length=10)
    private String name;

    @Lob
    private byte[] profile;

    @Column(nullable=false)
    private int age;

    @Column(nullable=false)
    private boolean gender; // true: male, false: female

    @Column(nullable=false, name = "date_pass")
    private LocalDateTime datePass; //별세일

    @Column(name = "date_death")
    private LocalDateTime dateDeath; //발인날짜

    private String location;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "created_at")
    private String createdAt;

    @ManyToMany
    @JoinTable(
            name = "late_owner",  // 중간 테이블을 생성하여 many-to-many 관계를 설정합니다.
            joinColumns = @JoinColumn(name = "late_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> owners;  // Owner 정보를 List로 저장


    @OneToMany(mappedBy = "late")
    private List<Gallery> galleries;
}
