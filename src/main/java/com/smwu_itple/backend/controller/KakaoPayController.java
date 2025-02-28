package com.smwu_itple.backend.controller;

import com.smwu_itple.backend.dto.request.KakaoApproveRequest;
import com.smwu_itple.backend.dto.request.KakaoReadyRequest;
import com.smwu_itple.backend.dto.response.KakaoApproveResponse;
import com.smwu_itple.backend.dto.response.KakaoReadyResponse;
import com.smwu_itple.backend.infra.api.ApiResponse;
import com.smwu_itple.backend.infra.api.FailureStatus;
import com.smwu_itple.backend.infra.api.SuccessStatus;
import com.smwu_itple.backend.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class KakaoPayController {
    private final KakaoPayService kakaoPayService;

    /** 결제 요청 */
    @PostMapping(value = "/ready", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> readyToKakaoPay(@RequestBody KakaoReadyRequest request) {
        try {
            KakaoReadyResponse response = kakaoPayService.kakaoPayReady(request);
            return ApiResponse.onSuccess(response, SuccessStatus._PAYMENT_READY_SUCCESS);
        } catch (Exception e) {
            return ApiResponse.onFailure(null, FailureStatus._BAD_REQUEST, e.getMessage());
        }
    }


    /** 결제 성공 처리 */
    @PostMapping("/success")
    public ResponseEntity<ApiResponse> afterPayRequest(@RequestBody KakaoApproveRequest request) {
        try {
            KakaoApproveResponse response = kakaoPayService.kakaoPayApprove(request);
            return ApiResponse.onSuccess(response, SuccessStatus._PAYMENT_APPROVE_SUCCESS);
        } catch (Exception e) {
            return ApiResponse.onFailure(null, FailureStatus._BAD_REQUEST, e.getMessage());
        }
    }

    /** 결제 취소 */
    @GetMapping("/cancel")
    public ResponseEntity<ApiResponse> cancel() {
        return ApiResponse.onFailure(null, FailureStatus._BAD_REQUEST, "결제가 취소되었습니다.");
    }

    /** 결제 실패 */
    @GetMapping("/fail")
    public ResponseEntity<ApiResponse> fail() {
        return ApiResponse.onFailure(null, FailureStatus._BAD_REQUEST, "결제가 실패하였습니다.");
    }
}
