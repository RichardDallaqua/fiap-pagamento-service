package com.fiap.paymentms.producer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagamentoConcluidoDTO {

    private String orderIdentifier;
    private String status;

}
