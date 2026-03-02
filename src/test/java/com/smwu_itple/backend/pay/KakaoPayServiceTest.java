package com.smwu_itple.backend.pay;

import com.smwu_itple.backend.pay.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KakaoPayServiceTest {
    /**
     * READY 테스트
     * Payment 없음 → 카카오 ready 호출 1번 + save 됨
     * Payment READY 존재 → 카카오 호출 0번 + 캐시 반환
     * Payment APPROVED 존재 → 예외
     *
     * APPROVE 테스트
     * Payment 없음 → “준비 정보 없음” 예외
     * Payment READY → 카카오 approve 호출 + 금액 일치면 APPROVED
     * Payment READY + 금액 불일치 응답 → FAIL + 예외
     * Payment APPROVED → 이미 처리된 결제 + 예외
     */

    @Mock private KakaoPayProperties payProperties;
    @Mock private PaymentRepository paymentRepository;
    @Mock private RestTemplate restTemplate;   // 외부 호출 가짜 응답

    @InjectMocks
    private KakaoPayService kakaoPayService;

    // ──────────────────────────────────────────────
    // kakaoPayReady
    // ──────────────────────────────────────────────

    @Test
    void ready_성공_READY_상태로_저장() {
        // given
        given(payProperties.getCid()).willReturn("TC0ONETIME");
        given(payProperties.getSecretKey()).willReturn("test-secret-key");

        KakaoReadyRequest request = readyRequest("order1", 10000);
        given(paymentRepository.findByPartnerOrderId("order1")).willReturn(Optional.empty());

        KakaoReadyResponse fakeResponse = new KakaoReadyResponse();
        fakeResponse.setTid("T123");
        fakeResponse.setNext_redirect_pc_url("https://mockpay.kakao.com/pc/order1");
        given(restTemplate.postForObject(anyString(), any(), eq(KakaoReadyResponse.class)))
                .willReturn(fakeResponse);

        // when
        KakaoReadyResponse result = kakaoPayService.kakaoPayReady(request);

        // then
        assertThat(result.getTid()).isEqualTo("T123");
        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    void ready_중복_READY_상태면_카카오_재호출_없이_캐시_반환() {
        // given
        KakaoReadyRequest request = readyRequest("order1", 10000);
        Payment existing = Payment.ofReady("order1", "T123", 10000, "https://mockpay.kakao.com/pc/order1");
        given(paymentRepository.findByPartnerOrderId("order1")).willReturn(Optional.of(existing));

        // when
        KakaoReadyResponse result = kakaoPayService.kakaoPayReady(request);

        // then: 카카오 API 호출 없음
        verify(restTemplate, never()).postForObject(anyString(), any(), any());
        assertThat(result.getTid()).isEqualTo("T123");
        assertThat(result.getNext_redirect_pc_url()).isEqualTo("https://mockpay.kakao.com/pc/order1");
    }

    @Test
    void ready_APPROVED_상태면_예외() {
        // given
        KakaoReadyRequest request = readyRequest("order1", 10000);
        Payment existing = Payment.ofReady("order1", "T123", 10000, "https://...");
        existing.approve();  // APPROVED 상태로 전이

        given(paymentRepository.findByPartnerOrderId("order1")).willReturn(Optional.of(existing));

        // when & then
        assertThatThrownBy(() -> kakaoPayService.kakaoPayReady(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("처리 불가능한 결제 상태");
    }

    // ──────────────────────────────────────────────
    // kakaoPayApprove
    // ──────────────────────────────────────────────

    @Test
    void approve_성공_APPROVED_상태로_전이() {
        // given
        given(payProperties.getCid()).willReturn("TC0ONETIME");
        given(payProperties.getSecretKey()).willReturn("test-secret-key");

        KakaoApproveRequest request = approveRequest("order1", "pgToken1");
        Payment payment = Payment.ofReady("order1", "T123", 10000, "https://...");
        given(paymentRepository.findByPartnerOrderId("order1")).willReturn(Optional.of(payment));

        KakaoApproveResponse fakeResponse = approveResponse(10000);
        given(restTemplate.postForObject(anyString(), any(), eq(KakaoApproveResponse.class)))
                .willReturn(fakeResponse);

        // when
        kakaoPayService.kakaoPayApprove(request);

        // then: 금액 일치 → APPROVED
        assertThat(payment.getStatus().name()).isEqualTo("APPROVED");
    }

    @Test
    void approve_금액_불일치시_FAIL_처리_후_예외() {
        // given
        KakaoApproveRequest request = approveRequest("order1", "pgToken1");
        Payment payment = Payment.ofReady("order1", "T123", 10000, "https://...");
        given(paymentRepository.findByPartnerOrderId("order1")).willReturn(Optional.of(payment));

        // 카카오가 다른 금액을 반환 (변조 시도)
        KakaoApproveResponse fakeResponse = approveResponse(5000);
        given(restTemplate.postForObject(anyString(), any(), eq(KakaoApproveResponse.class)))
                .willReturn(fakeResponse);

        // when & then
        assertThatThrownBy(() -> kakaoPayService.kakaoPayApprove(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("결제 금액 불일치");

        assertThat(payment.getStatus().name()).isEqualTo("FAIL");
    }

    @Test
    void approve_이미_APPROVED_상태면_예외() {
        // given
        KakaoApproveRequest request = approveRequest("order1", "pgToken1");
        Payment payment = Payment.ofReady("order1", "T123", 10000, "https://...");
        payment.approve();  // 이미 승인된 상태

        given(paymentRepository.findByPartnerOrderId("order1")).willReturn(Optional.of(payment));

        // when & then: 이중 승인 차단
        assertThatThrownBy(() -> kakaoPayService.kakaoPayApprove(request))
                .isInstanceOf(IllegalStateException.class);

        // 카카오 API 재호출 없음
        verify(restTemplate, never()).postForObject(anyString(), any(), any());
    }

    // ──────────────────────────────────────────────
    // helpers
    // ──────────────────────────────────────────────

    private KakaoReadyRequest readyRequest(String orderId, int amount) {
        KakaoReadyRequest r = new KakaoReadyRequest();
        r.setOrderId(orderId);
        r.setUserId("user1");
        r.setItemName("테스트 상품");
        r.setTotalAmount(amount);
        r.setVatAmount(amount / 10);
        return r;
    }

    private KakaoApproveRequest approveRequest(String orderId, String pgToken) {
        KakaoApproveRequest r = new KakaoApproveRequest(orderId, "user1", pgToken);
        return r;
    }

    private KakaoApproveResponse approveResponse(int total) {
        Amount amount = new Amount();
        amount.setTotal(total);
        KakaoApproveResponse response = new KakaoApproveResponse();
        response.setAmount(amount);
        return response;
    }
}