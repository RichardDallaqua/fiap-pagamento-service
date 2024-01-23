package com.fiap.paymentms.model.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class OrderVO {

    private String title;
    private List<String> items;
    private String orderIdentifier;
    private BigDecimal totalAmount;

    private String paymentStatus;
}
