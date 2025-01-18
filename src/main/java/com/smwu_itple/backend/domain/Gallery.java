package com.smwu_itple.backend.domain;

import com.smwu_itple.backend.domain.Attachment;
import com.smwu_itple.backend.domain.Late;
import com.smwu_itple.backend.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "gallery")
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gallery_id")
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @OneToOne
    @JoinColumn(name = "attachment_id")
    private Attachment attachment;

    @ManyToOne
    @JoinColumn(name = "late_id")
    private Late late;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
