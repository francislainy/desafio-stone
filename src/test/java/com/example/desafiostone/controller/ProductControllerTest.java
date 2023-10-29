package com.example.desafiostone.controller;

import com.example.desafiostone.model.Card;
import com.example.desafiostone.model.Client;
import com.example.desafiostone.model.Product;
import com.example.desafiostone.model.Transaction;
import com.example.desafiostone.service.ProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.desafiostone.utils.Util.convertToNewObject;
import static com.example.desafiostone.utils.Util.jsonStringFromObject;
import static java.time.LocalDate.now;
import static java.util.UUID.randomUUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductControllerTest {

    @MockBean
    ProductService productService;

    @Autowired
    MockMvc mockMvc;

    String productResponseJson;
    String productRequestJson;
    String transactionRequestJson;

    UUID productId = randomUUID();
    Product requestProduct;
    Product responseProduct;
    Transaction requestTransaction;

    @BeforeAll
    void setUp() {
        requestProduct = Product.builder()
                .title("anyProduct")
                .price(BigDecimal.TEN)
                .zipcode("78993-000")
                .seller("João da Silva")
                .thumbnail("https://cdn.awsli.com.br/600x450/21/21351/produto/3853007/f66e8c63ab.jpg")
                .date(now())
                .build();

        responseProduct = convertToNewObject(requestProduct, Product.class);
        responseProduct.setId(productId);

        productResponseJson = jsonStringFromObject(responseProduct);
        productRequestJson = jsonStringFromObject(requestProduct);

        requestTransaction = Transaction.builder()
                .totalToPay(BigDecimal.TEN)
                .client(Client.builder()
                        .id(randomUUID())
                        .name("anyClient")
                        .build())
                .card(Card.builder()
                        .id(randomUUID())
                        .cardNumber("1111-1111-1111-1111")
                        .value(7990)
                        .cvv(789)
                        .cardHolderName("anyName")
                        .expDate("10/27")
                        .build())
                .build();

        transactionRequestJson = jsonStringFromObject(requestTransaction);
    }

    @Test
    void getProduct() throws Exception {
        when(productService.getProduct(any())).thenReturn(responseProduct);

        mockMvc.perform(get("/starstore/product/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(content().json(productResponseJson)
                );
    }

    @Test
    void getProducts() throws Exception {
        when(productService.getProducts()).thenReturn(List.of(responseProduct));

        List<Product> productList = new ArrayList<>();
        productList.add(responseProduct);

        String jsonResponse = jsonStringFromObject(productList);
        mockMvc.perform(get("/starstore/products/", productId))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse)
                );
    }

    @Test
    void createProduct() throws Exception {
        when(productService.createProduct(any())).thenReturn(responseProduct);

        mockMvc.perform(post("/starstore/product/")
                        .content(productRequestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(productResponseJson)
                );
    }

    @Test
    void buyProduct() throws Exception {
        when(productService.buyProduct(any())).thenReturn(requestTransaction);

        mockMvc.perform(post("/starstore/buy/")
                        .content(transactionRequestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()
                );
    }
}
