package com.smwu_itple.backend.service;

import com.smwu_itple.backend.dto.KakaoPayProperties;
import com.smwu_itple.backend.dto.request.KakaoApproveRequest;
import com.smwu_itple.backend.dto.request.KakaoReadyRequest;
import com.smwu_itple.backend.dto.response.KakaoApproveResponse;
import com.smwu_itple.backend.dto.response.KakaoReadyResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class KakaoPayService {
    private final KakaoPayProperties payProperties;
    private final RestTemplate restTemplate = new RestTemplate();
    private KakaoReadyResponse kakaoReady;

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "SECRET_KEY " + payProperties.getSecretKey());
        headers.set("Content-Type", "application/json");
        return headers;
    }

    /** 결제 준비 요청 */
    public KakaoReadyResponse kakaoPayReady(KakaoReadyRequest request) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("cid", payProperties.getCid());
        parameters.put("partner_order_id", request.getOrderId()); // 프론트에서 받은 주문 ID
        parameters.put("partner_user_id", request.getUserId()); // 프론트에서 받은 사용자 ID
        parameters.put("item_name", request.getItemName()); // 상품명
        parameters.put("quantity", 1); // 수량
        parameters.put("total_amount", request.getTotalAmount()); // 총 금액
        parameters.put("vat_amount", request.getVatAmount()); // 부가세
        parameters.put("tax_free_amount", 0); // 면세 금액
        parameters.put("approval_url", "http://localhost:8081/api/payment/success"); // 결제 성공 URL
        parameters.put("fail_url", "http://localhost:8081/api/payment/fail"); // 결제 실패 URL
        parameters.put("cancel_url", "http://localhost:8081/api/payment/cancel"); // 결제 취소 URL

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
        kakaoReady = restTemplate.postForObject(
                "https://open-api.kakaopay.com/online/v1/payment/ready",
                requestEntity, KakaoReadyResponse.class);

        return kakaoReady;
    }

    /** 결제 승인 요청 */
    public KakaoApproveResponse kakaoPayApprove(KakaoApproveRequest request) {
        if (kakaoReady == null) {
            throw new IllegalStateException("카카오페이 결제 준비가 완료되지 않았습니다.");
        }

        Map<String, String> parameters = new HashMap<>();
        parameters.put("cid", payProperties.getCid());
        parameters.put("tid", kakaoReady.getTid()); // 결제 준비 시 받은 TID 사용
        parameters.put("partner_order_id", request.getOrderId()); // 프론트에서 받은 주문 ID
        parameters.put("partner_user_id", request.getUserId()); // 프론트에서 받은 사용자 ID
        parameters.put("pg_token", request.getPgToken()); // 프론트에서 받은 PG 토큰

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
        return restTemplate.postForObject(
                "https://open-api.kakaopay.com/online/v1/payment/approve",
                requestEntity, KakaoApproveResponse.class);
    }
}
