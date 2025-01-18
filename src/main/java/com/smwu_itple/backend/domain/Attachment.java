package com.smwu_itple.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "attachment")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id")
    private Long id;

    @Column(nullable = false)
    private String uploadPath;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String modifiedName;

    @Column(name = "created_at")
    private String createdAt;

    @OneToOne
    @JoinColumn(name = "message_id")
    private Message message;

    @OneToOne
    @JoinColumn(name = "gallery_id")
    private Gallery gallery;
}
