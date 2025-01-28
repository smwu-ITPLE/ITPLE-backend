package com.smwu_itple.backend.domain;

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

    @Column(nullable = false)
    private String passwd;

    @Lob
    private String profile;

    @Column(nullable=false)
    private int age;

    @Column(nullable=false)
    private String gender;

    @Column(nullable=false, name = "date_pass")
    private LocalDateTime datePass; //별세일

    @Column(name = "date_death")
    private LocalDateTime dateDeath; //발인날짜

    private String location;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @OneToMany(mappedBy = "late", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Owner> owners; // 상주 정보

    @OneToMany(mappedBy = "late", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Archive> archives; // 아카이브 정보
}
