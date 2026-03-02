package com.smwu_itple.backend.pay;

import com.smwu_itple.backend.pay.dto.KakaoApproveRequest;
import com.smwu_itple.backend.pay.dto.KakaoApproveResponse;
import com.smwu_itple.backend.pay.dto.KakaoPayProperties;
import com.smwu_itple.backend.pay.dto.KakaoReadyRequest;
import com.smwu_itple.backend.pay.dto.KakaoReadyResponse;
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
    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate = new RestTemplate();

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
        parameters.put("partner_order_id", request.getOrderId());
        parameters.put("partner_user_id", request.getUserId());
        parameters.put("item_name", request.getItemName());
        parameters.put("quantity", 1);
        parameters.put("total_amount", request.getTotalAmount());
        parameters.put("vat_amount", request.getVatAmount());
        parameters.put("tax_free_amount", 0);
        parameters.put("approval_url", "http://localhost:8081/api/payment/success");
        parameters.put("fail_url", "http://localhost:8081/api/payment/fail?orderId=" + request.getOrderId());
        parameters.put("cancel_url", "http://localhost:8081/api/payment/cancel?orderId=" + request.getOrderId());

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
        KakaoReadyResponse response = restTemplate.postForObject(
                "https://open-api.kakaopay.com/online/v1/payment/ready",
                requestEntity, KakaoReadyResponse.class);

        if (response == null) {
            throw new IllegalStateException("카카오페이 결제 준비 응답이 없습니다.");
        }

        // 결제 준비 상태를 DB에 저장
        Payment payment = Payment.ofReady(request.getOrderId(), response.getTid(), request.getTotalAmount());
        paymentRepository.save(payment);
        log.info("결제 준비 저장 - orderId: {}, tid: {}", request.getOrderId(), response.getTid());

        return response;
    }

    /** 결제 승인 요청 */
    public KakaoApproveResponse kakaoPayApprove(KakaoApproveRequest request) {
        Payment payment = paymentRepository.findByPartnerOrderId(request.getOrderId())
                .orElseThrow(() -> new IllegalStateException("결제 준비 정보를 찾을 수 없습니다. orderId: " + request.getOrderId()));

        Map<String, String> parameters = new HashMap<>();
        parameters.put("cid", payProperties.getCid());
        parameters.put("tid", payment.getTid());
        parameters.put("partner_order_id", request.getOrderId());
        parameters.put("partner_user_id", request.getUserId());
        parameters.put("pg_token", request.getPgToken());

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
        KakaoApproveResponse response = restTemplate.postForObject(
                "https://open-api.kakaopay.com/online/v1/payment/approve",
                requestEntity, KakaoApproveResponse.class);

        payment.approve();
        log.info("결제 승인 완료 - orderId: {}, tid: {}", request.getOrderId(), payment.getTid());

        return response;
    }

    /** 결제 취소 처리 */
    public void kakaoPayCancel(String orderId) {
        paymentRepository.findByPartnerOrderId(orderId).ifPresent(payment -> {
            payment.cancel();
            log.info("결제 취소 - orderId: {}", orderId);
        });
    }

    /** 결제 실패 처리 */
    public void kakaoPayFail(String orderId) {
        paymentRepository.findByPartnerOrderId(orderId).ifPresent(payment -> {
            payment.fail();
            log.info("결제 실패 - orderId: {}", orderId);
        });
    }
}