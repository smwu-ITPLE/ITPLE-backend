package com.smwu_itple.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "archive")
public class Archive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "archive_id")
    private Long id;

    private String nickname;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "late_id")
    private Late late;
}
