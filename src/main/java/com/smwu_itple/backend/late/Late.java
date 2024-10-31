package com.smwu_itple.backend.late;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Late {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lateId;

    @Column(nullable=false, length=50)
    private String lateName;

    @Lob
    private byte[] lateProfile;

    @Column(nullable=false)
    private int lateAge;

    @Column(nullable=false)
    private boolean lateGender; //0 남성 1 여성

    @Column(nullable=false)
    private LocalDateTime lateDate1; //별세일

    private LocalDateTime lateDate2; //발인날짜
    private String lateLocation;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String lateContent;
}
