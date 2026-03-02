package com.smwu_itple.backend.pay;

import com.smwu_itple.backend.pay.dto.KakaoApproveRequest;
import com.smwu_itple.backend.pay.dto.KakaoApproveResponse;
import com.smwu_itple.backend.pay.dto.KakaoReadyRequest;
import com.smwu_itple.backend.pay.dto.KakaoReadyResponse;
import com.smwu_itple.backend.infra.api.ApiResponse;
import com.smwu_itple.backend.infra.api.FailureStatus;
import com.smwu_itple.backend.infra.api.SuccessStatus;
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
        KakaoReadyResponse response = kakaoPayService.kakaoPayReady(request);
        return ApiResponse.onSuccess(response, SuccessStatus._PAYMENT_READY_SUCCESS);
    }

    /** 결제 성공 처리 */
    @GetMapping("/success")
    public ResponseEntity<ApiResponse> success(
            @RequestParam String pg_token,
            @RequestParam String orderId) {

        KakaoApproveRequest request = new KakaoApproveRequest(orderId, "user1", pg_token);

        KakaoApproveResponse response = kakaoPayService.kakaoPayApprove(request);
        return ApiResponse.onSuccess(response, SuccessStatus._PAYMENT_APPROVE_SUCCESS);
    }

    /** 결제 취소 */
    @GetMapping("/cancel")
    public ResponseEntity<ApiResponse> cancel(@RequestParam(required = false) String orderId) {
        kakaoPayService.kakaoPayCancel(orderId);
        return ApiResponse.onFailure(null, FailureStatus._BAD_REQUEST, "결제가 취소되었습니다.");
    }

    /** 결제 실패 */
    @GetMapping("/fail")
    public ResponseEntity<ApiResponse> fail(@RequestParam(required = false) String orderId) {
        kakaoPayService.kakaoPayFail(orderId);
        return ApiResponse.onFailure(null, FailureStatus._BAD_REQUEST, "결제가 실패하였습니다.");
    }
}