package com.fiap.paymentms.controller;

import com.fiap.paymentms.model.dto.OrderInfoDTO;
import com.fiap.paymentms.model.vo.OrderVO;
import com.fiap.paymentms.service.PaymentService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments/")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/{order-id}")
    public OrderVO getByOrderIdentifier(@PathParam("order-id") String orderIdentifier){
        return paymentService.findByOrderIdentifierAndBuildVO(orderIdentifier);
    }
}
