package com.fiap.paymentms.producer;

import com.fiap.paymentms.producer.dto.PagamentoConcluidoDTO;
import com.fiap.paymentms.producer.dto.QrCodeDTO;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Component
public class PagamentoProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${queues.qr_code_gerado}")
    private String qrCodeGeradoQueue;

    @Value("${queues.pagamento_concluido}")
    private String pagamentoConcluidoQueue;


    public void qrCodeGerado(QrCodeDTO qrCode) {
        rabbitTemplate.convertAndSend(qrCodeGeradoQueue, toQrCodeMessage(qrCode));
    }

    public void pagamentoConcluido(PagamentoConcluidoDTO pagamentoConcluidoDTO){
        rabbitTemplate.convertAndSend(pagamentoConcluidoQueue, toPagamentoConcluidoMessage(pagamentoConcluidoDTO));
    }

    @SneakyThrows
    public static String toQrCodeMessage(QrCodeDTO qrCode) {
        Map message = new HashMap<String, String>();
        var qrCodeToString = new String(qrCode.getQrCode(), "UTF-8");
        message.put("orderIdentifier", qrCode.getOrderIdentifier());
        message.put("qrCode", qrCodeToString);

        return new Gson().toJson(message);
    }

    public static String toPagamentoConcluidoMessage(PagamentoConcluidoDTO pagamentoConcluidoDTO){
        Map message = new HashMap<String, String>();
        message.put("orderIdentifier", pagamentoConcluidoDTO.getOrderIdentifier());
        message.put("status", pagamentoConcluidoDTO.getStatus());

        return new Gson().toJson(message);
    }
}
