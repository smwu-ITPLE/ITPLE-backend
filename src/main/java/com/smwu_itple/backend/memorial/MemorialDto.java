package com.smwu_itple.backend.memorial;

import com.smwu_itple.backend.user.User;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class MemorialDto {
    private String title;
    private String content;
    private String mask;
    private byte[] image;
    private User memorialUser;
}
