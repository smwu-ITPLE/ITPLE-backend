package com.smwu_itple.backend.pay;

import com.smwu_itple.backend.pay.dto.Amount;
import com.smwu_itple.backend.pay.dto.KakaoApproveRequest;
import com.smwu_itple.backend.pay.dto.KakaoApproveResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class KakaoPayOptimisticLockTest {

    @Autowired
    private KakaoPayService kakaoPayService;

    @Autowired
    private PaymentRepository paymentRepository;

    @MockBean
    private RestTemplate restTemplate; // 외부 API만 Mock

    @Test
    void 낙관적락_동시승인_테스트() throws InterruptedException {

        // 1. READY 상태 Payment 생성
        String orderId = "order-" + System.currentTimeMillis();
        Payment payment = Payment.ofReady(orderId, "T123", 10000, "url");
        paymentRepository.save(payment);

        // 2. 카카오 응답 Mock
        KakaoApproveResponse fakeResponse = new KakaoApproveResponse();
        Amount amount = new Amount();
        amount.setTotal(10000);
        fakeResponse.setAmount(amount);

        when(restTemplate.postForObject(
                anyString(),
                any(),
                eq(KakaoApproveResponse.class)
        )).thenReturn(fakeResponse);

        // 3. 동시에 2개 요청 실행
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(2);

        Runnable task = () -> {
            try {
                kakaoPayService.kakaoPayApprove(
                        new KakaoApproveRequest(orderId, "user1", "pgToken")
                );
            } catch (OptimisticLockingFailureException | IllegalStateException e) {
                System.out.println("이미 처리된 결제입니다: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        };

        executor.submit(task);
        executor.submit(task);

        latch.await();

        // 4. 결과 확인
        Payment result = paymentRepository.findByPartnerOrderId(orderId).orElseThrow();

        assertThat(result.getStatus().name()).isEqualTo("APPROVED");
    }
}