package com.fiap.paymentms.fixture;

import com.fiap.paymentms.model.dto.OrderInfoDTO;
import com.fiap.paymentms.model.entities.Payment;
import com.fiap.paymentms.model.enumerated.PaymentStatus;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

public class TestFixtures {

    public static OrderInfoDTO generateOrderInfoDTO(){
        return OrderInfoDTO.builder().orderIdentifier(UUID.randomUUID().toString())
                .items(Arrays.asList("refrigerante", "hamburguer"))
                .title("teste").totalAmount(BigDecimal.TEN).build();
    }

    public static Payment paymentFixture(){
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setItems(Arrays.asList("refrigerante", "hamburguer"));
        payment.setPaymentStatus(PaymentStatus.SUCCESS.name());
        payment.setTotalAmount(BigDecimal.TEN);
        payment.setOrderIdentifier(UUID.randomUUID().toString());
        return payment;
    }
}
