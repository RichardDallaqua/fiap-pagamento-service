package com.fiap.paymentms.bdd;

import com.fiap.paymentms.fixture.TestFixtures;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

public class StepDefinition {

    private Response response;

    private final String ENDPOINT = "http://localhost:9090/fiap-payments/api/v1/payments/generate/qr-code";

    @Quando("realizar uma requisição para o endpoint de geração de QR-CODE com dados válidos")
    public void realizarRequisicao() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(TestFixtures.generateOrderInfoDTO())
                .post(ENDPOINT);
    }
    @Entao("o código de status da resposta deve ser {int}")
    public void o_codigo_de_status_da_resposta_deve_ser(Integer statusCode) {
        response.then()
                .statusCode(statusCode);
    }

    @E("resposta deve conter o QR-CODE")
    public void responseDeveConter() {
        Assertions.assertNotNull(response.body());
    }
}
