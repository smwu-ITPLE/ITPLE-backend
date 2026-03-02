package com.smwu_itple.backend.pay;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByPartnerOrderId(String partnerOrderId);
}