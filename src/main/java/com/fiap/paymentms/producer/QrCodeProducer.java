package com.fiap.paymentms.producer;

import com.fiap.paymentms.model.dto.QrCodeDTO;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QrCodeProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    public void qrCodeGerado(QrCodeDTO qrCode) {
        rabbitTemplate.convertAndSend("qr_code_gerado", qrCode);
    }
}
