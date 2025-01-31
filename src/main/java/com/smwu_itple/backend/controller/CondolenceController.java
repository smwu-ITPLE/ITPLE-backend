package com.smwu_itple.backend.controller;

import com.smwu_itple.backend.dto.ArchiveDto;
import com.smwu_itple.backend.dto.request.MessageCreateRequest;
import com.smwu_itple.backend.dto.response.LateShareResponse;
import com.smwu_itple.backend.dto.response.MessageCreateResponse;
import com.smwu_itple.backend.infra.api.ApiResponse;
import com.smwu_itple.backend.infra.api.FailureStatus;
import com.smwu_itple.backend.infra.api.SuccessStatus;
import com.smwu_itple.backend.infra.exception.UnauthorizedException;
import com.smwu_itple.backend.infra.util.SessionUtil;
import com.smwu_itple.backend.service.CondolenceService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/lates/{lateId}")
public class CondolenceController {
    private final CondolenceService condolenceService;

    @PostMapping(value = "/message", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> createMessage(@PathVariable Long lateId,
                                                     @RequestPart(value = "attachment", required = false) MultipartFile attachment, // 사진 파일
                                                     @RequestPart("data") MessageCreateRequest messagecreateRequest,
                                                     HttpSession session) {
        try {
            Long senderId = SessionUtil.getUserIdFromSession(session);
            MessageCreateResponse response = condolenceService.createMessage(lateId, senderId, attachment, messagecreateRequest);
            return ApiResponse.onSuccess(response, SuccessStatus._POST_MESSAGE_CREATE_SUCCESS);
        } catch (UnauthorizedException e) {
            return ApiResponse.onFailure(null, FailureStatus._UNAUTHORIZED, e.getMessage());
        } catch (Exception e) {
            return ApiResponse.onFailure(null, FailureStatus._NOT_FOUND, e.getMessage());
        }
    }
}
