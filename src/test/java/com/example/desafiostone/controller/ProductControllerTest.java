package com.example.desafiostone.controller;

import com.example.desafiostone.model.Product;
import com.example.desafiostone.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @MockBean
    ProductService productService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getProduct() throws Exception {
        UUID productId = randomUUID();

        Product product = Product.builder()
                .id(productId)
                .build();
        when(productService.getProduct(any())).thenReturn(product);

        mockMvc.perform(get("/starstore/product/{productId}", productId))
                .andExpect(status().isOk());
    }
}
