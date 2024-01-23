package com.fiap.paymentms.repository;

import com.fiap.paymentms.model.entities.Payment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PaymentRepository extends MongoRepository<Payment, ObjectId> {

    Optional<Payment> findByOrderIdentifier(String orderIdentifier);
}
