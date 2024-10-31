package com.smwu_itple.backend.archieve;

import com.smwu_itple.backend.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Archieve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long archieveId;

    @ManyToOne
    @JoinColumn(name = "archieve_user", nullable = false)
    private User archieveUser; // 글쓴이

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String archieveContent; // 편지

    @Column(nullable = false)
    private int archieveColor; // 1/2/3/4/5 중에서 선택
}
