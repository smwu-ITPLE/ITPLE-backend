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

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public static Payment ofReady(String partnerOrderId, String tid, int amount) {
        Payment payment = new Payment();
        payment.partnerOrderId = partnerOrderId;
        payment.tid = tid;
        payment.amount = amount;
        payment.status = PaymentStatus.READY;
        payment.createdAt = LocalDateTime.now();
        return payment;
    }

    public void approve() {
        this.status = PaymentStatus.APPROVED;
    }

    public void cancel() {
        this.status = PaymentStatus.CANCEL;
    }

    public void fail() {
        this.status = PaymentStatus.FAIL;
    }
}