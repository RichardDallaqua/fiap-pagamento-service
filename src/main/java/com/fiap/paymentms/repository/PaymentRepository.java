package com.fiap.paymentms.repository;

import com.fiap.paymentms.model.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByOrderIdentifier(String orderIdentifier);
}
