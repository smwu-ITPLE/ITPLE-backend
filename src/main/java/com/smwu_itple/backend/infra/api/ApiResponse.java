package com.smwu_itple.backend.infra.api;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class ApiResponse {
    private final Object data;
    private final String message;
    private final String status;

    public ApiResponse(Object data, String message, String status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public static ResponseEntity<ApiResponse> onSuccess(Object data, SuccessStatus successStatus) {
        return ResponseEntity.ok(new ApiResponse(data, successStatus.getMessage(), successStatus.name()));
    }

    public static ResponseEntity<ApiResponse> onFailure(Object data, FailureStatus failureStatus, String message) {
        return ResponseEntity.status(failureStatus.getHttpStatus())
                .body(new ApiResponse(data, message, failureStatus.name()));
    }
}