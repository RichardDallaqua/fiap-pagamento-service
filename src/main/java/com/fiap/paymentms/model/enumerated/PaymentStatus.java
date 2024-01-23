package com.fiap.paymentms.model.enumerated;

import java.util.Arrays;
import java.util.Optional;

public enum PaymentStatus {
    AWAITING,
    ERROR,
    REFUSED,
    SUCCESS;

    public static Optional<PaymentStatus> getByName(String name){
        return Arrays.stream(values()).filter(x -> x.name().equalsIgnoreCase(name)).findFirst();
    }
}
