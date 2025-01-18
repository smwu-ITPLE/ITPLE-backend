package com.smwu_itple.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name="name", nullable = false, length=10)
    private String name;

    @Column(unique = true, length=11)
    private String phonenumber;

    private String passwd;

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;

    @OneToMany(mappedBy = "user")
    private List<UserRole> roles;

    @OneToMany(mappedBy = "user")
    private List<Message> messages;

//    @OneToMany(mappedBy = "user")
//    private List<Archieve> archieves;


    //getter and setter
}
