package com.fiap.paymentms.model.entities;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Payment {


    private Long id;

    private String title;

    private List<String> items;

    private BigDecimal totalAmount;

    private String orderIdentifier;

    private String paymentStatus;
}
