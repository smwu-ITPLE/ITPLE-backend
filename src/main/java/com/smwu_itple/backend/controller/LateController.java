package com.smwu_itple.backend.controller;

import com.smwu_itple.backend.domain.Late;
import com.smwu_itple.backend.dto.ArchiveDto;
import com.smwu_itple.backend.dto.request.LateCreateRequest;
import com.smwu_itple.backend.dto.request.LateSearchRequest;
import com.smwu_itple.backend.dto.response.LateCreateResponse;
import com.smwu_itple.backend.dto.response.LateGetResponse;
import com.smwu_itple.backend.dto.response.LateOwnerResponse;
import com.smwu_itple.backend.dto.response.LateShareResponse;
import com.smwu_itple.backend.infra.api.ApiResponse;
import com.smwu_itple.backend.infra.api.FailureStatus;
import com.smwu_itple.backend.infra.api.SuccessStatus;
import com.smwu_itple.backend.infra.exception.UnauthorizedException;
import com.smwu_itple.backend.infra.util.SessionUtil;
import com.smwu_itple.backend.service.LateService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/lates")
public class LateController {
    private final LateService lateService;
    // 조문공간 생성
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> createLate(
            @RequestPart("profile") MultipartFile profileFile, // 사진 파일
            @RequestPart("data") LateCreateRequest lateCreateRequest, // JSON 데이터
            HttpSession session
    ) {
        try {
            Long userId = SessionUtil.getUserIdFromSession(session);
            LateCreateResponse response = lateService.createLate(profileFile, lateCreateRequest);
            return ApiResponse.onSuccess(response, SuccessStatus._POST_LATE_CREATE_SUCCESS);
        } catch (UnauthorizedException e) {
            return ApiResponse.onFailure(null, FailureStatus._UNAUTHORIZED, e.getMessage());
        } catch (Exception e) {
            return ApiResponse.onFailure(null, FailureStatus._NOT_FOUND, e.getMessage());
        }
    }

    //조문공간 검색
    @PostMapping("/search")
    public ResponseEntity<ApiResponse> searchLate(@RequestBody LateSearchRequest lateSearchRequest) {
        try {
            Late late = lateService.searchLate(lateSearchRequest);

            // 응답에 조문공간 URL 포함
            String lateUrl = "/api/lates/" + late.getId();
            return ApiResponse.onSuccess(Map.of(
                    "url", lateUrl // 페이지 접근 URL
            ), SuccessStatus._SEARCH_LATE_SUCCESS);
        } catch (IllegalArgumentException e) {
            return ApiResponse.onFailure(null, FailureStatus._NOT_FOUND, e.getMessage());
        }
    }

    // 특정 조문공간 삭제
    @DeleteMapping("/delete/{lateId}")
    public ResponseEntity<ApiResponse> deleteLate(@PathVariable Long lateId) {
        try {
            lateService.deleteLate(lateId);
            return ApiResponse.onSuccess(null, SuccessStatus._DELETE_LATE_SUCCESS);
        } catch (IllegalStateException e) {
            // 실패 응답 반환
            return ApiResponse.onFailure(null, FailureStatus._NOT_FOUND, "조문공간 삭제 실패: " + e.getMessage());
        }
    }

    //특정 조문공간 조회
    @GetMapping("/{lateId}")
    public ResponseEntity<ApiResponse> getLate(@PathVariable Long lateId) {
        try {
            LateGetResponse response = lateService.findLate(lateId);
            return ApiResponse.onSuccess(response, SuccessStatus._GET_LATE_SUCCESS);
        } catch (IllegalArgumentException e) {
            return ApiResponse.onFailure(null, FailureStatus._NOT_FOUND, e.getMessage());
        }
    }

    //특정 조문공간 공유
    @GetMapping("/share/{lateId}")
    public ResponseEntity<ApiResponse> shareLate(@PathVariable Long lateId, HttpSession session){
        try {
            Long userId = SessionUtil.getUserIdFromSession(session);
            LateShareResponse response = lateService.shareLate(lateId, userId);
            return ApiResponse.onSuccess(response, SuccessStatus._GET_LATE_SHARE_SUCCESS);
        } catch (UnauthorizedException e) {
            return ApiResponse.onFailure(null, FailureStatus._UNAUTHORIZED, e.getMessage());
        } catch (Exception e) {
            return ApiResponse.onFailure(null, FailureStatus._NOT_FOUND, e.getMessage());
        }
    }

    //채팅할 수 있는 상주 조회
    @GetMapping("/{lateId}/lateowner")
    public ResponseEntity<ApiResponse> getLateOwner(@PathVariable Long lateId) {
        try {
            List<LateOwnerResponse> response = lateService.getLateOwner(lateId);
            return ApiResponse.onSuccess(response, SuccessStatus._GET_LATEOWNER_SUCCESS);
        } catch (IllegalArgumentException e) {
            return ApiResponse.onFailure(null, FailureStatus._NOT_FOUND, e.getMessage());
        }
    }

    //아카이브 작성
    @PostMapping("/{lateId}/archive")
    public ResponseEntity<ApiResponse> createArchive(@PathVariable Long lateId, @RequestBody ArchiveDto archiveRequest) {
        try {
            ArchiveDto response = lateService.createArchive(lateId, archiveRequest);
            return ApiResponse.onSuccess(response, SuccessStatus._GET_LATE_SUCCESS);
        } catch (IllegalArgumentException e) {
            return ApiResponse.onFailure(null, FailureStatus._NOT_FOUND, e.getMessage());
        }
    }
}