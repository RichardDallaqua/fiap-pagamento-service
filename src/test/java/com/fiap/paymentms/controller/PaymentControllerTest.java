package com.fiap.paymentms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.paymentms.exception.NotFoundException;
import com.fiap.paymentms.exception.PaymentException;
import com.fiap.paymentms.fixture.TestFixtures;
import com.fiap.paymentms.model.vo.OrderVO;
import com.fiap.paymentms.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class PaymentControllerTest {

    public static final String ENDPOINT = "/api/v1/payments";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentService paymentService;

    @Test
    void shouldGenerateQrCode() throws Exception {
        doNothing().when(paymentService).generateQrCode(any());
        mockMvc.perform(post(ENDPOINT.concat("/generate/qr-code"))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestFixtures.generateOrderInfoDTO())))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void shouldFindPaymentByOrderIdentifier() throws Exception {
        when(paymentService.findByOrderIdentifierAndBuildVO(any())).thenReturn(OrderVO.builder().build());

        mockMvc.perform(get(ENDPOINT.concat("/aaa123"))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void shouldNotFindPaymentByOrderIdentifier() throws Exception {
        when(paymentService.findByOrderIdentifierAndBuildVO(any())).thenThrow(new NotFoundException("Não foi possível encontrar o registro"));

        mockMvc.perform(get(ENDPOINT.concat("/aaa123"))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void shouldNotUpdateStatusWhenNotFound() throws Exception {
        when(paymentService.updateStatus(any(), any())).thenThrow(new NotFoundException(""));

        mockMvc.perform(put(ENDPOINT.concat("/update-status?order=aaa123&status=ERROR"))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void shouldNotUpdateStatusWhenStatusNotFound() throws Exception {
        when(paymentService.updateStatus(any(), any())).thenThrow(new PaymentException(""));

        mockMvc.perform(put(ENDPOINT.concat("/update-status?order=aaa123&status=AAAAAA"))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void shouldUpdateStatu() throws Exception {
        when(paymentService.updateStatus(any(), any())).thenReturn(OrderVO.builder().build());

        mockMvc.perform(put(ENDPOINT.concat("/update-status?order=aaa123&status=AAAAAA"))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}