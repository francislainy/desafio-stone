package com.example.desafiostone.controller;

import com.example.desafiostone.model.Product;
import com.example.desafiostone.service.ProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static java.time.LocalDate.now;
import static java.util.UUID.randomUUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static utils.Util.convertToNewObject;
import static utils.Util.jsonStringFromObject;

@WebMvcTest(ProductController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductControllerTest {

    @MockBean
    ProductService productService;

    @Autowired
    MockMvc mockMvc;

    String productResponseJson;
    String productRequestJson;

    UUID productId = randomUUID();

    Product requestProduct;
    Product responseProduct;

    @BeforeAll
    void setUp() {
        requestProduct = Product.builder()
                .title("anyProduct")
                .zipcode("78993-000")
                .seller("João da Silva")
                .thumbnail("https://cdn.awsli.com.br/600x450/21/21351/produto/3853007/f66e8c63ab.jpg")
                .date(now())
                .build();


        responseProduct = convertToNewObject(requestProduct, Product.class);
        responseProduct.setId(productId);

        productResponseJson = jsonStringFromObject(responseProduct);
        productRequestJson = jsonStringFromObject(requestProduct);
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
    void createProduct() throws Exception {
        when(productService.createProduct(any())).thenReturn(responseProduct);

        mockMvc.perform(post("/starstore/product/"))
                .andExpect(content().json(productResponseJson))
                .andExpect(status().isOk())
                .andExpect(content().json(productResponseJson)
                );
    }
}
