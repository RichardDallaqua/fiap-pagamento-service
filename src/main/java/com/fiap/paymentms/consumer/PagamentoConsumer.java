package com.fiap.paymentms.consumer;

import com.fiap.paymentms.consumer.dto.RealizaPagamentoDTO;
import com.fiap.paymentms.model.dto.OrderInfoDTO;
import com.fiap.paymentms.service.PaymentService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
public class PagamentoConsumer {

    @Autowired
    private PaymentService paymentService;

    @RabbitListener(queues = {"${queue.gerar_qr_code}"})
    public void receiveQrCode(@Payload OrderInfoDTO orderInfo){
        paymentService.generateQrCode(orderInfo);
    }

    @RabbitListener(queues = {"${queue.realiza_pagamento}"})
    public void receivePagamento(@Payload RealizaPagamentoDTO realizaPagamentoDTO){
        paymentService.updateStatus(realizaPagamentoDTO.getOrderIdentifier(), realizaPagamentoDTO.getStatus());
    }
}
