package com.fiap.paymentms.exception;

import lombok.Getter;

@Getter
public class QrCodeGenerationException extends RuntimeException{
    private String message;

    public QrCodeGenerationException(String message){
        super(message);
    }
}
