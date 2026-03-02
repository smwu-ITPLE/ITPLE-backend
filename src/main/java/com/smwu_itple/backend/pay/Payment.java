package com.smwu_itple.backend.pay;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String partnerOrderId;  // 우리 시스템 주문번호

    private String tid;             // 카카오 결제 고유값

    @Column(nullable = false)
    private int amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Version
    private Long version;

    private String redirectPcUrl;      // ready 멱등성 응답용

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public static Payment ofReady(String partnerOrderId, String tid, int amount, String redirectPcUrl) {
        Payment payment = new Payment();
        payment.partnerOrderId = partnerOrderId;
        payment.tid = tid;
        payment.amount = amount;
        payment.redirectPcUrl = redirectPcUrl;
        payment.status = PaymentStatus.READY;
        payment.createdAt = LocalDateTime.now();
        return payment;
    }

    public boolean isReady() {
        return this.status == PaymentStatus.READY;
    }

    public void approve() {
        if (this.status != PaymentStatus.READY) {
            throw new IllegalStateException("승인 불가능한 상태");
        }
        this.status = PaymentStatus.APPROVED;
    }

    public void cancel() {
        if (this.status != PaymentStatus.READY) {
            throw new IllegalStateException("취소 불가능한 상태");
        }
        this.status = PaymentStatus.CANCEL;
    }

    public void fail() {
        this.status = PaymentStatus.FAIL;
    }
}