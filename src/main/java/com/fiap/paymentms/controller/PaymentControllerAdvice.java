package com.fiap.paymentms.controller;

import com.fiap.paymentms.exception.NotFoundException;
import com.fiap.paymentms.exception.PaymentException;
import com.fiap.paymentms.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PaymentControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException notFoundException){
        return ErrorResponse.builder().errorMessage(notFoundException.getMessage()).build();
    }

    @ExceptionHandler(PaymentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlePaymentException(PaymentException paymentException){
        return ErrorResponse.builder().errorMessage(paymentException.getMessage()).build();
    }
}
