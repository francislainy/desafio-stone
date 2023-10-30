package com.example.desafiostone.pact.consumer;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.provider.junitsupport.VerificationReports;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.example.desafiostone.constants.Constants.*;
import static com.example.desafiostone.utils.Util.getMockRequest;
import static java.util.UUID.fromString;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpMethod.GET;

@ExtendWith(PactConsumerTestExt.class)
@VerificationReports(value = {"markdown"}, reportDir = "target/pacts")
class GetHistoryForClientIT {

    Map<String, String> headers = new HashMap<>();

    String path = "/starstore/history/";
    UUID clientId = fromString("f0652d7b-1fb4-490d-9fbf-adc23c65b2df");

    @Pact(provider = PACT_PROVIDER, consumer = PACT_CONSUMER)
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");

        DslPart bodyReturned = PactDslJsonArray.arrayEachLike(2)
                .uuid("client_id", clientId)
                .uuid("purchase_id", randomUUID())
                .numberType("value", 1234)
                .date("19/08/2016", "dd/MM/YYYY", new Date())
                .stringType("card_number", "**** **** **** 1234")
                .close();

        return builder
                .given("A request to retrieve the history of transactions for a client")
                .uponReceiving("A request to retrieve the history of transactions for a client")
                .pathFromProviderState(path + "${clientId}", path + clientId)
                .method(String.valueOf(GET))
                .headers(headers)
                .willRespondWith()
                .body(bodyReturned)
                .status(200)
                .toPact();
    }

    @Test
    @PactTestFor(providerName = PACT_PROVIDER, port = MOCK_PACT_PORT, pactVersion = PactSpecVersion.V3)
    void runTest() {
        Response response = getMockRequest(headers).get(path + clientId);
        assertEquals(200, response.getStatusCode());
    }
}
