package com.smwu_itple.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileService {

    private final S3Service s3Service;

    // 파일 업로드 (S3 사용)
    public String uploadFile(MultipartFile file) throws IOException {
        return s3Service.uploadFile(file);
    }

    // 파일 삭제 (S3 사용)
    public void deleteFile(String fileUrl) {
        s3Service.deleteFile(fileUrl);
    }
}
