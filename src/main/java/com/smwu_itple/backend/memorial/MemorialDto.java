package com.smwu_itple.backend.memorial;

import com.smwu_itple.backend.archieve.ArchieveDTO;
import com.smwu_itple.backend.user.User;
import org.springframework.web.multipart.MultipartFile;
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
