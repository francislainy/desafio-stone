package com.example.desafiostone.utils;

import au.com.dius.pact.core.model.RequestResponseInteraction;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.dzieciou.testing.curl.CurlRestAssuredConfigFactory;
import com.github.dzieciou.testing.curl.Options;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.mapper.factory.Jackson2ObjectMapperFactory;
import io.restassured.specification.RequestSpecification;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpRequest;

import java.lang.reflect.Type;
import java.util.Map;

import static com.example.desafiostone.constants.Constants.MOCK_PACT_URL;
import static io.restassured.RestAssured.given;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Util {

    public static RequestSpecification getRequestSpecification() {

        /* Enables printing request as curl under the terminal as per https://github.com/dzieciou/curl-logger */
        Options options = Options.builder()
                .printMultiliner()
                .updateCurl(curl -> curl
                        .removeHeader("Host")
                        .removeHeader("User-Agent")
                        .removeHeader("Connection"))
                .build();

        RestAssuredConfig config = CurlRestAssuredConfigFactory.createConfig(options).objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory(
                new Jackson2ObjectMapperFactory() {
                    @Override
                    public ObjectMapper create(Type type, String charset) {
                        ObjectMapper om = new ObjectMapper().findAndRegisterModules();
                        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                        return om;
                    }
                }));

        return given()
                .config(config)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .urlEncodingEnabled(false)
                .when()
                .log()
                .everything();
    }


    public static String jsonStringFromObject(Object jsonObject) {
        if (jsonObject == null) {
            return "";
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String jsonStr;
        try {
            jsonStr = mapper.writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            throw new JacksonConversionException(e.getMessage());
        }
        return jsonStr;
    }

    public static <T> T objectFromJsonString(String json, Class<T> c) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructType(c));
        } catch (JsonProcessingException e) {
            throw new JacksonConversionException(e.getMessage());
        }
    }

    public static <T> T convertToNewObject(Object o, Class<T> c) {
        String json = jsonStringFromObject(o);
        return objectFromJsonString(json, c);
    }

    public static void logCurlFromPact(PactVerificationContext context, HttpRequest request, String baseUri) {
        String bodyParam = ((RequestResponseInteraction) context.getInteraction()).getRequest().getBody().valueAsString();
        String bodyResponse = ((RequestResponseInteraction) context.getInteraction()).getResponse().getBody().valueAsString();
        String method = ((RequestResponseInteraction) context.getInteraction()).getRequest().getMethod();

        String url = baseUri + request.getPath();

        Header[] headers = request.getHeaders();
        String headersString = "";
        for (Header s : headers) {
            headersString = headersString + "--header " + "'" + s.getName() + ": " + s.getValue() + "'" + "\\" + "\n";
        }

        String curl = """
                curl --request %s '%s' \
                %s --data-binary '%s' \
                --compressed --insecure --verbose
                """.formatted(method, url, headersString, bodyParam);


        log.debug(curl + "\n\n " + bodyResponse + "\n ---- \n\n");
    }

    public static RequestSpecification getMockRequest(Map<String, String> headers) {
        return getRequestSpecification().baseUri(MOCK_PACT_URL).headers(headers);
    }
}

