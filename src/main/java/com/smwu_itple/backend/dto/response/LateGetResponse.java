package com.smwu_itple.backend.dto.response;

import com.smwu_itple.backend.dto.ArchiveDto;
import com.smwu_itple.backend.dto.OwnerDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class LateGetResponse {
    private String name;
    private String profile;
    private int age; // 나이는 0 이상이어야 함
    private String gender;
    private LocalDateTime datePass; // 별세일 날짜
    private LocalDateTime dateDeath; // 발인 날짜
    private String location;
    private List<OwnerDto> owners;
    private List<ArchiveDto> archives;
}
