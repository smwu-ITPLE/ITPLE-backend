package com.smwu_itple.backend.dto;

import com.smwu_itple.backend.domain.User;
import lombok.Data;

import java.util.List;

@Data
public class MemorialDto {
    private String title;
    private String content;
    private String mask;
    private byte[] image;
    private User memorialUser;
    private List<ArchieveDTO> archieveDTOs;
}
