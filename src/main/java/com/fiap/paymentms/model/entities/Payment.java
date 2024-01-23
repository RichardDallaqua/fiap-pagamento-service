package com.fiap.paymentms.model.entities;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.util.List;

@Data
@Document(collection = "payment")
public class Payment {

    @Id
    private ObjectId id;

    private String title;

    @Field(targetType = FieldType.ARRAY)
    private List<String> items;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal totalAmount;

    private String orderIdentifier;

    private String paymentStatus;
}
