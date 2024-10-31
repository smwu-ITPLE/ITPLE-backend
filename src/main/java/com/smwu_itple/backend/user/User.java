package com.smwu_itple.backend.user;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length=50)
    private String userName;

    @Column(unique = true, length=11)
    private String userPhone;

    @Column(nullable = false, length=255)
    private String userPasswd;

    //getter and setter
}
