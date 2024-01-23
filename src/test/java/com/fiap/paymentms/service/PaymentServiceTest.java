package com.fiap.paymentms.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.paymentms.events.publisher.PaymentEventPublisher;
import com.fiap.paymentms.exception.PaymentException;
import com.fiap.paymentms.exception.QrCodeGenerationException;
import com.fiap.paymentms.fixture.TestFixtures;
import com.fiap.paymentms.model.entities.Payment;
import com.fiap.paymentms.model.enumerated.PaymentStatus;
import com.fiap.paymentms.model.vo.OrderVO;
import com.fiap.paymentms.repository.PaymentRepository;
import com.google.zxing.WriterException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private PaymentEventPublisher paymentEventPublisher;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void shouldNotGenerateQrCodeWhenPaymentAlreadyExists() {
        when(paymentRepository.findByOrderIdentifier(any())).thenReturn(Optional.of(new Payment()));

        PaymentException exception =  assertThrows(PaymentException.class, () -> paymentService.generateQrCode(TestFixtures.generateOrderInfoDTO()));
        assertNotNull(exception);
    }

    @Test
    void shouldNotGenerateQrCodeWhenThrowsException() throws WriterException {
        when(paymentRepository.findByOrderIdentifier(any())).thenReturn(Optional.empty());

        QrCodeGenerationException exception =  assertThrows(QrCodeGenerationException.class, () -> paymentService.generateQrCode(TestFixtures.generateOrderInfoDTO()));
        assertNotNull(exception);
    }

    @Test
    void shouldUpdatePaymentStatus() {
        when(paymentRepository.findByOrderIdentifier(any())).thenReturn(Optional.of(TestFixtures.paymentFixture()));
        when(paymentRepository.save(any())).thenReturn(TestFixtures.paymentFixture());
        paymentService.updatePaymentStatus(TestFixtures.generateOrderInfoDTO().getOrderIdentifier(), PaymentStatus.AWAITING);
    }

    @Test
    void shouldNotUpdatePaymentStatus() {
        when(paymentRepository.findByOrderIdentifier(any())).thenReturn(Optional.empty());
        PaymentException paymentException = assertThrows(PaymentException.class, () -> paymentService.updatePaymentStatus(TestFixtures.generateOrderInfoDTO().getOrderIdentifier(), PaymentStatus.AWAITING));
        assertNotNull(paymentException);
    }

    @Test
    void findByOrderIdentifierAndBuildVO() {
        when(paymentRepository.findByOrderIdentifier(any())).thenReturn(Optional.of(TestFixtures.paymentFixture()));
        OrderVO orderVO = paymentService.findByOrderIdentifierAndBuildVO("aaa123");
        assertNotNull(orderVO);
    }

    @Test
    void shouldNotUpdateStatusWhenSucess() {
        when(paymentRepository.findByOrderIdentifier(any())).thenReturn(Optional.of(TestFixtures.paymentFixture()));
        PaymentException paymentException = assertThrows(PaymentException.class, () -> paymentService.updateStatus("aaa123", "ERROR"));
        assertNotNull(paymentException);
    }

    @Test
    void shouldNotUpdateStatusWhenStatusNotFound() {
        PaymentException paymentException = assertThrows(PaymentException.class, () -> paymentService.updateStatus("aaa123", "AAAAAAAAAAAA"));
        assertNotNull(paymentException);
    }
}