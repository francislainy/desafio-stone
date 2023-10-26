package com.example.desafiostone.service;

import com.example.desafiostone.entity.ProductEntity;
import com.example.desafiostone.mapper.ProductMapper;
import com.example.desafiostone.model.Product;
import com.example.desafiostone.repository.ProductRepository;
import com.example.desafiostone.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static java.time.LocalDate.now;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static com.example.desafiostone.utils.Util.convertToNewObject;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductServiceTest {

    @InjectMocks
    ProductServiceImpl productService;

    ProductRepository productRepository;

    @Spy
    ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    UUID productId = randomUUID();
    LocalDate date;

    ProductEntity expectedProductEntity;

    ProductEntity requestProductEntity;

    @BeforeAll
    void setUp() {
        productRepository = mock(ProductRepository.class);
        date = now();

        requestProductEntity = ProductEntity.builder()
                .title("Blusa do Imperio")
                .zipcode("78993-000")
                .seller("João da Silva")
                .thumbnail("https://cdn.awsli.com.br/600x450/21/21351/produto/3853007/f66e8c63ab.jpg")
                .date(date)
                .build();

        expectedProductEntity = convertToNewObject(requestProductEntity, ProductEntity.class);
        expectedProductEntity.setId(productId);
    }

    @Test
    void getProduct() {
        Product expectedProduct = productMapper.toModel(expectedProductEntity);

        when(productRepository.findById(any())).thenReturn(Optional.ofNullable(expectedProductEntity));
        when(productMapper.toModel(any())).thenReturn(expectedProduct);

        Product actualProduct = productService.getProduct(productId);

        assertNotNull(actualProduct);
        assertAll(
                () -> assertEquals(expectedProduct.getTitle(), actualProduct.getTitle()),
                () -> assertEquals(expectedProduct.getZipcode(), actualProduct.getZipcode()),
                () -> assertEquals(expectedProduct.getSeller(), actualProduct.getSeller()),
                () -> assertEquals(expectedProduct.getThumbnail(), actualProduct.getThumbnail()),
                () -> assertEquals(expectedProduct.getDate(), actualProduct.getDate())
        );
    }

    @Test
    void createProduct() {
        when(productRepository.save(any())).thenReturn(expectedProductEntity);

        Product expectedProduct = productMapper.toModel(expectedProductEntity);
        Product requestProduct = productMapper.toModel(requestProductEntity);
        Product actualProduct = productService.createProduct(requestProduct);

        assertNotNull(actualProduct);
        assertAll(
                () -> assertEquals(expectedProduct.getTitle(), actualProduct.getTitle()),
                () -> assertEquals(expectedProduct.getZipcode(), actualProduct.getZipcode()),
                () -> assertEquals(expectedProduct.getSeller(), actualProduct.getSeller()),
                () -> assertEquals(expectedProduct.getThumbnail(), actualProduct.getThumbnail()),
                () -> assertEquals(expectedProduct.getDate(), actualProduct.getDate())
        );
    }
}
