package com.fiap.paymentms.commons;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SenderConfig {

    @Value("${queue02.qr_code_gerado}")
    private String qrCodegerado;

    @Bean
    public Queue geraQrCode() {
        return new Queue(qrCodegerado, true);
    }
}
