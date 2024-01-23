package com.fiap.paymentms.events;

import com.fiap.paymentms.model.enumerated.PaymentStatus;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PaymentEvent extends ApplicationEvent {

    private final String orderIdentifier;
    private final PaymentStatus paymentStatus;

    public PaymentEvent(Object source, String orderIdentifier, PaymentStatus paymentStatus) {
        super(source);
        this.orderIdentifier = orderIdentifier;
        this.paymentStatus = paymentStatus;
    }

}
