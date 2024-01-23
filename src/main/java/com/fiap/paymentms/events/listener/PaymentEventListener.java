package com.fiap.paymentms.events.listener;

import com.fiap.paymentms.events.PaymentEvent;
import com.fiap.paymentms.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventListener {

    @Autowired
    private PaymentService paymentService;

    @EventListener(PaymentEvent.class)
    public void handlePaymentEvent(PaymentEvent event) {
        paymentService.updatePaymentStatus(event.getOrderIdentifier(), event.getPaymentStatus());
    }
}
