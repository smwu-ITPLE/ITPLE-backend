package com.smwu_itple.backend.dto.request;

import com.smwu_itple.backend.domain.Owner;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class LateCreateRequest {

    private String name;
    private int age; // 나이는 0 이상이어야 함
    private String gender;
    private LocalDateTime datePass; // 별세일 날짜
    private LocalDateTime dateDeath; // 발인 날짜
    private String location;
    private String content;
    private List<Owner> owners; // 여러 명의 상주 정보를 포함
}