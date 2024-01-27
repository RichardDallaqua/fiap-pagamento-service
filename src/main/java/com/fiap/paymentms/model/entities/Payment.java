package com.fiap.paymentms.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private List<String> items;

    private BigDecimal totalAmount;

    private String orderIdentifier;

    private String paymentStatus;
}
