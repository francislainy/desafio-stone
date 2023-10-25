package com.example.desafiostone.repository;

import com.example.desafiostone.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    void getProduct() {
        ProductEntity productEntity = ProductEntity.builder()
                .title("anyProduct")
                .build();

        productEntity = productRepository.save(productEntity);

        Optional<ProductEntity> optionalProductEntity = productRepository.findById(productEntity.getId());

        assertTrue(optionalProductEntity.isPresent());
    }
}
