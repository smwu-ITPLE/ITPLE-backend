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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class KakaoPayService {
    private final KakaoPayProperties payProperties;
    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;   // Bean 주입 (테스트에서 Mock 교체 가능)

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "SECRET_KEY " + payProperties.getSecretKey());
        headers.set("Content-Type", "application/json");
        return headers;
    }

    /** 결제 준비 요청 */
    public KakaoReadyResponse kakaoPayReady(KakaoReadyRequest request) {
        // 멱등성: 같은 orderId로 이미 Payment가 존재하면 상태에 따라 처리
        Optional<Payment> existing = paymentRepository.findByPartnerOrderId(request.getOrderId());
        if (existing.isPresent()) {
            Payment p = existing.get();
            if (p.isReady()) {
                log.info("중복 ready 요청 - 기존 결제 정보 반환. orderId: {}", request.getOrderId());
                KakaoReadyResponse cached = new KakaoReadyResponse();
                cached.setTid(p.getTid());
                cached.setNext_redirect_pc_url(p.getRedirectPcUrl());
                return cached;
            }
            throw new IllegalStateException("처리 불가능한 결제 상태: " + p.getStatus() + ", orderId: " + request.getOrderId());
        }

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("cid", payProperties.getCid());
        parameters.put("partner_order_id", request.getOrderId());
        parameters.put("partner_user_id", request.getUserId());
        parameters.put("item_name", request.getItemName());
        parameters.put("quantity", 1);
        parameters.put("total_amount", request.getTotalAmount());
        parameters.put("vat_amount", request.getVatAmount());
        parameters.put("tax_free_amount", 0);
        parameters.put("approval_url",
                "http://localhost:8081/api/payment/success?orderId=" + request.getOrderId());
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
        Payment payment = Payment.ofReady(
                request.getOrderId(), response.getTid(),
                request.getTotalAmount(), response.getNext_redirect_pc_url());
        paymentRepository.save(payment);
        log.info("결제 준비 저장 - orderId: {}, tid: {}", request.getOrderId(), response.getTid());

        return response;
    }

    /** 결제 승인 요청 */
    public KakaoApproveResponse kakaoPayApprove(KakaoApproveRequest request) {
        Payment payment = paymentRepository.findByPartnerOrderId(request.getOrderId())
                .orElseThrow(() -> new IllegalStateException("결제 준비 정보를 찾을 수 없습니다. orderId: " + request.getOrderId()));

        if (!payment.isReady()) {
            throw new IllegalStateException("이미 처리된 결제");
        }

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

        if (response == null) {
            payment.fail();
            throw new IllegalStateException("카카오페이 승인 응답이 없습니다.");
        }

        // 금액 검증: 카카오 응답 금액 vs DB 저장 금액
        int confirmedAmount = response.getAmount().getTotal();
        if (confirmedAmount != payment.getAmount()) {
            payment.fail();
            throw new IllegalStateException(
                    "결제 금액 불일치. 예상: " + payment.getAmount() + ", 실제: " + confirmedAmount);
        }

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