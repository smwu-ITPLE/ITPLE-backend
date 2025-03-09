package com.smwu_itple.backend.infra.exception;

import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Map<String, String>> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        // 서버에서 최대 업로드 크기 확인 (2GB)
        long maxSize = 2L * 1024  * 1024 * 1024;
        System.out.println("🚨 업로드된 파일이 서버에서 설정한 크기(" + maxSize + " 바이트)를 초과했습니다.");

        // JSON 형식으로 응답 반환 + UTF-8 인코딩 명시
        Map<String, String> response = Map.of(
                "error", "파일 크기가 너무 큽니다.",
                "message", "최대 업로드 크기를 초과했습니다.",
                "maxSize", maxSize + " bytes"
        );

        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(response);
    }
}
