package com.fiap.paymentms.producer;

import com.fiap.paymentms.producer.dto.PagamentoConcluidoDTO;
import com.fiap.paymentms.producer.dto.QrCodeDTO;
import com.google.gson.Gson;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PagamentoProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    public void qrCodeGerado(QrCodeDTO qrCode) {
        rabbitTemplate.convertAndSend("qr_code_gerado", toQrCodeMessage(qrCode));
    }

    public void pagamentoConcluido(PagamentoConcluidoDTO pagamentoConcluidoDTO){
        rabbitTemplate.convertAndSend("pagamento_concluido", toPagamentoConcluidoMessage(pagamentoConcluidoDTO));
    }

    public static String toQrCodeMessage(QrCodeDTO qrCode){
        Map message = new HashMap<String, String>();
        message.put("orderIdentifier", qrCode.getOrderIdentifier());
        message.put("qrCode", qrCode.getQrCode());

        return new Gson().toJson(message);
    }

    public static String toPagamentoConcluidoMessage(PagamentoConcluidoDTO pagamentoConcluidoDTO){
        Map message = new HashMap<String, String>();
        message.put("orderIdentifier", pagamentoConcluidoDTO.getOrderIdentifier());
        message.put("status", pagamentoConcluidoDTO.getStatus());

        return new Gson().toJson(message);
    }
}
