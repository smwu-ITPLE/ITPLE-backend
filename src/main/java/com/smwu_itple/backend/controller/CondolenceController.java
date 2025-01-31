package com.smwu_itple.backend.controller;

import com.smwu_itple.backend.dto.request.MessageCreateRequest;
import com.smwu_itple.backend.dto.request.PayCreateRequest;
import com.smwu_itple.backend.dto.response.LateOwnerResponse;
import com.smwu_itple.backend.dto.response.MessageCreateResponse;
import com.smwu_itple.backend.dto.response.PayCreateResponse;
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

import java.io.IOException;
import java.util.List;

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

    @PostMapping(value = "/pay")
    public ResponseEntity<ApiResponse> CreatePay(@PathVariable Long lateId,
                                                 @RequestBody PayCreateRequest paycreateRequest,
                                                 HttpSession session){
        try {
            Long senderId = SessionUtil.getUserIdFromSession(session);
            PayCreateResponse response = condolenceService.createPay(lateId, senderId, paycreateRequest);
            return ApiResponse.onSuccess(response, SuccessStatus._POST_PAY_CREATE_SUCCESS);
        } catch (UnauthorizedException e) {
            return ApiResponse.onFailure(null, FailureStatus._UNAUTHORIZED, e.getMessage());
        } catch (Exception e) {
            return ApiResponse.onFailure(null, FailureStatus._NOT_FOUND, e.getMessage());
        }
    }
    @GetMapping(value = "/manage/message")
    public ResponseEntity<ApiResponse> ManageMessage(@PathVariable Long lateId) {
        try {
            List<MessageCreateResponse> response = condolenceService.getMessageList(lateId);
            return ApiResponse.onSuccess(response, SuccessStatus._GET_MESSAGELIST_SUCCESS);
        } catch (Exception e) {
            return ApiResponse.onFailure(null, FailureStatus._NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value = "/manage/pay")
    public ResponseEntity<ApiResponse> ManagePay(@PathVariable Long lateId) {
        try {
            List<PayCreateResponse> response = condolenceService.getPayList(lateId);
            return ApiResponse.onSuccess(response, SuccessStatus._GET_PAYLIST_SUCCESS);
        } catch (Exception e) {
            return ApiResponse.onFailure(null, FailureStatus._NOT_FOUND, e.getMessage());
        }
    }
}
