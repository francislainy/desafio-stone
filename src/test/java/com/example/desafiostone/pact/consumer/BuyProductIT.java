package com.example.desafiostone.pact.consumer;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.provider.junitsupport.VerificationReports;
import com.example.desafiostone.model.Card;
import com.example.desafiostone.model.Client;
import com.example.desafiostone.model.Transaction;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.example.desafiostone.constants.Constants.*;
import static com.example.desafiostone.utils.Util.getMockRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpMethod.POST;

@ExtendWith(PactConsumerTestExt.class)
@VerificationReports(value = {"markdown"}, reportDir = "target/pacts")
class BuyProductIT {

    Map<String, String> headers = new HashMap<>();

    String path = "/starstore/buy/";
    UUID clientId = UUID.fromString("f0652d7b-1fb4-490d-9fbf-adc23c65b2df");

    @Pact(provider = PACT_PROVIDER, consumer = PACT_CONSUMER)
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");

        DslPart bodyReceived = new PactDslJsonBody()
                .numberType("total_to_pay", 1234)
                .object("client")
                .uuid("client_id", clientId)
                .stringType("client_name", "Luke Skywalkwer")
                .close()
                .object("card")
                .stringType("card_number", "1234-1234-1234")
                .integerType("value", 7990)
                .integerType("cvv", 789)
                .stringType("card_holder_name", "Luke Skywalker")
                .date("exp_date", "MM/dd", new Date())
                .close()
                .close();

        return builder
                .given("A request to buy a product")
                .uponReceiving("A request to buy a product")
                .body(bodyReceived)
                .path(path)
                .method(String.valueOf(POST))
                .headers(headers)
                .willRespondWith()
                .status(201)
                .toPact();
    }

    @Test
    @PactTestFor(providerName = PACT_PROVIDER, port = MOCK_PACT_PORT, pactVersion = PactSpecVersion.V3)
    void runTest() {
        Transaction transaction = Transaction.builder()
                .totalToPay(BigDecimal.TEN)
                .client(Client.builder()
                        .id(clientId)
                        .name("anyClient")
                        .build())
                .card(Card.builder()
                        .cardNumber("1111-1111-1111-1111")
                        .value(7990)
                        .cvv(789)
                        .cardHolderName("Luke Skywalker")
                        .expDate("10/27")
                .build())
                .build();

        Response response = getMockRequest(headers).body(transaction).post(path);
        assertEquals(201, response.getStatusCode());
    }
}
