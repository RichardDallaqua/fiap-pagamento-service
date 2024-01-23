package com.fiap.paymentms.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.paymentms.events.publisher.PaymentEventPublisher;
import com.fiap.paymentms.exception.NotFoundException;
import com.fiap.paymentms.exception.PaymentException;
import com.fiap.paymentms.exception.QrCodeGenerationException;
import com.fiap.paymentms.model.dto.OrderInfoDTO;
import com.fiap.paymentms.model.entities.Payment;
import com.fiap.paymentms.model.enumerated.PaymentStatus;
import com.fiap.paymentms.model.vo.OrderVO;
import com.fiap.paymentms.repository.PaymentRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PaymentService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentEventPublisher paymentEventPublisher;

    public byte[] generateQrCode(OrderInfoDTO orderInfo){
        if (!paymentRepository.findByOrderIdentifier(orderInfo.getOrderIdentifier()).isPresent()){
            try{
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bitMatrix = qrCodeWriter.encode(objectMapper.writeValueAsString(orderInfo), BarcodeFormat.QR_CODE, 350, 350);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
                this.createPayment(orderInfo);
                paymentEventPublisher.publish(orderInfo.getOrderIdentifier(), PaymentStatus.SUCCESS);
                return outputStream.toByteArray();
            }catch (Exception ex) {
                throw new QrCodeGenerationException(ex.getMessage());
            }
        }else{
            throw new PaymentException("Esse pagamento já foi criado, consulte o status");
        }
    }

    public void updatePaymentStatus(String orderIdentifier, PaymentStatus paymentStatus){
        Optional<Payment> paymentToUpdate = paymentRepository.findByOrderIdentifier(orderIdentifier);
        if (paymentToUpdate.isPresent()){
            paymentToUpdate.get().setPaymentStatus(paymentStatus.name());
            paymentRepository.save(paymentToUpdate.get());
        }else{
            throw new PaymentException("Não foi possível atualizar o status de pagamento do pedido : " + orderIdentifier);
        }
    }

    public OrderVO findByOrderIdentifierAndBuildVO(String orderIdentifier){
        Payment payment = this.findPaymentByOrderIdentifier(orderIdentifier);
        return OrderVO.builder().orderIdentifier(payment.getOrderIdentifier()).paymentStatus(payment.getPaymentStatus())
                .items(payment.getItems()).title(payment.getTitle()).totalAmount(payment.getTotalAmount())
                .build();
    }

    public OrderVO updateStatus(String orderIdentifier, String status){
        if(PaymentStatus.getByName(status).isEmpty()) {
            throw new PaymentException("Status " + status + " não existente");
        }else{
            Payment payment = this.findPaymentByOrderIdentifier(orderIdentifier);
            if(Stream.of(PaymentStatus.SUCCESS, PaymentStatus.ERROR, PaymentStatus.REFUSED).anyMatch(x -> x.name().equalsIgnoreCase(payment.getPaymentStatus()))) {
                throw new PaymentException("Não foi possível alterar o status do pagamento");
            }
            payment.setPaymentStatus(PaymentStatus.getByName(status).get().name());
            paymentRepository.save(payment);
            return OrderVO.builder().orderIdentifier(payment.getOrderIdentifier()).paymentStatus(payment.getPaymentStatus())
                    .items(payment.getItems()).title(payment.getTitle()).totalAmount(payment.getTotalAmount())
                    .build();
        }
    }


    private Payment findPaymentByOrderIdentifier(String orderIdentifier){
        return paymentRepository.findByOrderIdentifier(orderIdentifier).orElseThrow(() -> new NotFoundException("Não foi possível localizar o registro"));
    }

    private void createPayment(OrderInfoDTO orderInfo){
        Payment payment = new Payment();
        payment.setId(ObjectId.get());
        payment.setOrderIdentifier(orderInfo.getOrderIdentifier());
        payment.setPaymentStatus(PaymentStatus.AWAITING.name());
        payment.setTitle(orderInfo.getTitle());
        payment.setItems(orderInfo.getItems());
        payment.setTotalAmount(orderInfo.getTotalAmount());
        paymentRepository.save(payment);
    }
}
