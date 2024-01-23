package com.fiap.paymentms.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderInfoDTO {

    private String title;
    private List<String> items;
    private String orderIdentifier;
    private BigDecimal totalAmount;
}
