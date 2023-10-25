package com.example.desafiostone.service;

import com.example.desafiostone.entity.ProductEntity;
import com.example.desafiostone.mapper.ProductMapper;
import com.example.desafiostone.model.Product;
import com.example.desafiostone.repository.ProductRepository;
import com.example.desafiostone.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    ProductRepository productRepository;

    @Spy
    ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Test
    void getProduct() {
        UUID productId = randomUUID();
        ProductEntity expectedProduct = ProductEntity.builder()
                .id(productId)
                .title("Blusa do Imperio") //todo: add remaining fields - 25/10/2023
                .build();

        Product product = productMapper.toModel(expectedProduct);

        when(productRepository.findById(any())).thenReturn(Optional.ofNullable(expectedProduct));
        when(productMapper.toModel(any())).thenReturn(product);

        Product actualProduct = productService.getProduct(productId);

        assertNotNull(actualProduct);
        assertAll(
                () -> assertEquals(expectedProduct.getTitle(), actualProduct.getTitle())
        );
    }
}
