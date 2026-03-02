package com.smwu_itple.backend.user;

import com.smwu_itple.backend.message.Message;
import com.smwu_itple.backend.owner.Owner;
import com.smwu_itple.backend.pay.Pay;
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Owner> owners;

    @OneToMany(mappedBy = "sender")
    private List<Message> messages;

    @OneToMany(mappedBy = "sender")
    private List<Pay> pays;
}
