package com.smwu_itple.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "archieve")
public class Archieve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "archieve_id")
    private Long id;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Column(nullable = false)
    private String color;

    @ManyToOne
    @JoinColumn(name = "memorial_id")
    private Memorial memorial;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
}
