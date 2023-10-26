package com.example.desafiostone.pact.provider;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.VerificationReports;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.example.desafiostone.config.BasePostgresConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.hc.core5.http.HttpRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static com.example.desafiostone.utils.Util.getRequestSpecification;
import static com.example.desafiostone.utils.Util.logCurlFromPact;

@Provider("MY_PROVIDER")
/* Uncomment this and comment @PactBroker instead to test locally by pasting a .json file for the contract under
 the target/pacts folder */
@PactFolder("target/pacts")
// @PactBroker(host = BROKER_PACT_URL, consumers = {"MY_CONSUMER"})
@VerificationReports(value = {"markdown"}, reportDir = "target/pacts")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:clean-up.sql", "classpath:init.sql"}, executionPhase = BEFORE_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProviderIT extends BasePostgresConfig {

    @LocalServerPort
    int port;

    RequestSpecification rq;

    @BeforeAll
    void setUp() {
        Map<String, String> headers = new HashMap<>();
        rq = getRequestSpecification().baseUri("http://localhost:" + port)
                .contentType(ContentType.JSON)
                .headers(headers);
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactTestTemplate(PactVerificationContext context, HttpRequest request) {
        logCurlFromPact(context, request, "http://localhost:" + port);
        context.verifyInteraction();
    }

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port, ""));
    }

    @State("A request to retrieve a product")
    Map<String, Object> getProduct() {
        Map<String, Object> map = new HashMap<>();
        map.put("productId", "d3256c76-62d7-4481-9d1c-a0ccc4da380f");
        return map;
    }

    @State("A request to create a product")
    void createProduct() {

    }

}
