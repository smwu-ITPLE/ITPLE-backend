package com.smwu_itple.backend.late;

import com.smwu_itple.backend.gallery.Gallery;
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

    @OneToMany(mappedBy = "late")
    private List<UserRole> roles;

    @OneToMany(mappedBy = "late")
    private List<Gallery> galleries;
}
