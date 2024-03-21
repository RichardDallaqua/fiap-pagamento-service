package com.fiap.paymentms.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(schema = "sch_payments", name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String items;

    private BigDecimal totalAmount;

    private String orderIdentifier;

    private String paymentStatus;
}
