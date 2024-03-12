package com.fiap.paymentms.consumer;

import com.fiap.paymentms.consumer.dto.RealizaPagamentoDTO;
import com.fiap.paymentms.model.dto.OrderInfoDTO;
import com.fiap.paymentms.service.PaymentService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class PagamentoConsumer {

    @Autowired
    private final PaymentService paymentService;

    @Autowired
    private Gson gson;

    @RabbitListener(queues = {"${queue01.gerar_qr_code}"})
    public void receiveQrCode(@Payload String message){
        HashMap<String, String> mensagem = gson.fromJson(message, HashMap.class);
        OrderInfoDTO orderInfo = fromMessageToOrderInfoDTO(mensagem);
        paymentService.generateQrCode(orderInfo);
    }

    @RabbitListener(queues = {"${queue03.realiza_pagamento}"})
    public void receivePagamento(@Payload String message){
        HashMap<String, String> mensagem = gson.fromJson(message, HashMap.class);
        RealizaPagamentoDTO realizaPagamentoDTO = fromMessageToRealizaPagamentoDTO(mensagem);
        paymentService.updateStatus(realizaPagamentoDTO.getOrderIdentifier(), realizaPagamentoDTO.getStatus());
    }

    private static OrderInfoDTO fromMessageToOrderInfoDTO(Map mensagem) {
        return OrderInfoDTO.builder()
                .title(mensagem.get("title").toString())
                .items(List.of(mensagem.get("items").toString()))
                .orderIdentifier(mensagem.get("orderIdentifier").toString())
                .totalAmount(new BigDecimal(mensagem.get("totalAmount").toString()))
                .build();
    }

    private static RealizaPagamentoDTO fromMessageToRealizaPagamentoDTO(Map mensagem){
        return RealizaPagamentoDTO.builder()
                .orderIdentifier(mensagem.get("orderIdentifier").toString())
                .status(mensagem.get("status").toString())
                .build();
    }
}
