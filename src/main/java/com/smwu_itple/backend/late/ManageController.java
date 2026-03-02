package com.smwu_itple.backend.late;

import com.smwu_itple.backend.late.dto.LateShareResponse;
import com.smwu_itple.backend.late.dto.LateSimpleResponse;
import com.smwu_itple.backend.message.dto.MessageCreateResponse;
import com.smwu_itple.backend.pay.dto.PayCreateResponse;
import com.smwu_itple.backend.pay.dto.PayListResponse;
import com.smwu_itple.backend.pay.dto.PaySumResponse;
import com.smwu_itple.backend.infra.api.ApiResponse;
import com.smwu_itple.backend.infra.api.FailureStatus;
import com.smwu_itple.backend.infra.api.SuccessStatus;
import com.smwu_itple.backend.infra.exception.UnauthorizedException;
import com.smwu_itple.backend.infra.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/manage")
public class ManageController {
    private final ManageService manageService;

    //나의 기록 - 메시지 조회
    @GetMapping("/message")
    public ResponseEntity<ApiResponse> readMessagelist(HttpSession session) {
        try {
            Long userId = SessionUtil.getUserIdFromSession(session);
            List<MessageCreateResponse> response = manageService.readMessagelist(userId);
            return ApiResponse.onSuccess(response, SuccessStatus._GET_MESSAGELIST_SUCCESS);
        } catch (Exception e) {
            return ApiResponse.onFailure(null, FailureStatus._NOT_FOUND, e.getMessage());
        }
    }

    //나의 기록 - 조의금 조회
    @GetMapping("/pay")
    public ResponseEntity<ApiResponse> readPaylist(HttpSession session) {
        try {
            Long userId = SessionUtil.getUserIdFromSession(session);
            List<PayCreateResponse> response = manageService.readPaylist(userId);
            return ApiResponse.onSuccess(response, SuccessStatus._GET_PAYLIST_SUCCESS);
        } catch (Exception e) {
            return ApiResponse.onFailure(null, FailureStatus._NOT_FOUND, e.getMessage());
        }
    }

    //상주로 있는 조문공간 리스트
    @GetMapping("/latelist")
    public ResponseEntity<ApiResponse> getLatelist(HttpSession session){
        try {
            Long userId = SessionUtil.getUserIdFromSession(session);
            List<LateSimpleResponse> Response = manageService.getLatelist(userId);
            return ApiResponse.onSuccess(Response, SuccessStatus._GET_USER_LATELIST_SUCCESS);
        } catch (UnauthorizedException e) {
            return ApiResponse.onFailure(null, FailureStatus._UNAUTHORIZED, e.getMessage());
        } catch (Exception e) {
            return ApiResponse.onFailure(null, FailureStatus._NOT_FOUND, e.getMessage());
        }
    }

    //특정 조문공간 공유
    @GetMapping("/{lateId}/share")
    public ResponseEntity<ApiResponse> shareLate(@PathVariable Long lateId, HttpSession session){
        try {
            Long userId = SessionUtil.getUserIdFromSession(session);
            LateShareResponse response = manageService.shareLate(lateId, userId);
            return ApiResponse.onSuccess(response, SuccessStatus._GET_LATE_SHARE_SUCCESS);
        } catch (UnauthorizedException e) {
            return ApiResponse.onFailure(null, FailureStatus._UNAUTHORIZED, e.getMessage());
        } catch (Exception e) {
            return ApiResponse.onFailure(null, FailureStatus._NOT_FOUND, e.getMessage());
        }
    }

    //조문 메시지 조회
    @GetMapping(value = "/{lateId}/message")
    public ResponseEntity<ApiResponse> ManageMessage(@PathVariable Long lateId) {
        try {
            List<MessageCreateResponse> response = manageService.getMessageList(lateId);
            return ApiResponse.onSuccess(response, SuccessStatus._GET_MESSAGELIST_SUCCESS);
        } catch (Exception e) {
            return ApiResponse.onFailure(null, FailureStatus._NOT_FOUND, e.getMessage());
        }
    }

    //조문 부의금 조회
    @GetMapping(value = "/{lateId}/pay")
    public ResponseEntity<ApiResponse> ManagePay(@PathVariable Long lateId) {
        try {
            List<PaySumResponse> paySumResponse = manageService.getPaySum(lateId);
            List<PayCreateResponse> payCreateResponse = manageService.getPayList(lateId);
            PayListResponse response = new PayListResponse(paySumResponse, payCreateResponse);

            return ApiResponse.onSuccess(response, SuccessStatus._GET_PAYLIST_SUCCESS);
        } catch (Exception e) {
            return ApiResponse.onFailure(null, FailureStatus._NOT_FOUND, e.getMessage());
        }
    }
}
