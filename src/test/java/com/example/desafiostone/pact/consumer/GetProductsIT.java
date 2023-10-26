package com.example.desafiostone.pact.consumer;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.*;

import static com.example.desafiostone.constants.Constants.*;
import static com.example.desafiostone.utils.Util.getMockRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpMethod.GET;

@ExtendWith(PactConsumerTestExt.class)
class GetProductsIT {

    Map<String, String> headers = new HashMap<>();

    String path = "/starstore/products/";
    UUID productId = UUID.fromString("f0652d7b-1fb4-490d-9fbf-adc23c65b2df");

    @Pact(provider = PACT_PROVIDER, consumer = PACT_CONSUMER)
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");

        DslPart bodyReturned = PactDslJsonArray.arrayEachLike()
                .uuid("id", productId)
                .stringType("title", "Blusa do Imperio")
                .numberType("price", 7990)
                .stringType("zipcode", "78993-000")
                .stringType("seller", "João da Silva")
                .stringType("thumbnail", "https://cdn.awsli.com.br/600x450/21/21351/produto/3853007/f66e8c63ab.jpg")
                .date("date", "dd/MM/yyyy", new Date())
                .close();

        return builder
                .given("A request to retrieve a list of products")
                .uponReceiving("A request to retrieve a list of products")
                .path(path)
                .method(String.valueOf(GET))
                .headers(headers)
                .willRespondWith()
                .status(200)
                .body(Objects.requireNonNull(bodyReturned))
                .toPact();
    }

    @Test
    @PactTestFor(providerName = PACT_PROVIDER, port = MOCK_PACT_PORT, pactVersion = PactSpecVersion.V3)
    void runTest() {
        Response response = getMockRequest(headers).get(path);
        assertEquals(200, response.getStatusCode());
    }
}
