package com.fiap.paymentms.events.publisher;

import com.fiap.paymentms.events.PaymentEvent;
import com.fiap.paymentms.model.enumerated.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Async
    public void publish(String orderIdentifier, PaymentStatus paymentStatus){
        applicationEventPublisher.publishEvent(new PaymentEvent(this, orderIdentifier, paymentStatus));
    }
}
