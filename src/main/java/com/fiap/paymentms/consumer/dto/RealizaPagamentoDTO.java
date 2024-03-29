package com.fiap.paymentms.consumer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RealizaPagamentoDTO {

    private String orderIdentifier;
    private String status;
}
