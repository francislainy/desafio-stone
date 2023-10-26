package com.example.desafiostone.repository;

import com.example.desafiostone.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    void createAndRetrieveProduct() {
        ProductEntity productEntity = ProductEntity.builder()
                .title("anyProduct")
                .zipcode("78993-000")
                .seller("João da Silva")
                .thumbnail("https://cdn.awsli.com.br/600x450/21/21351/produto/3853007/f66e8c63ab.jpg")
                .date(now())
                .build();

        productEntity = productRepository.save(productEntity);

        Optional<ProductEntity> optionalProductEntity = productRepository.findById(productEntity.getId());

        assertTrue(optionalProductEntity.isPresent());

        ProductEntity finalProductEntity = productEntity;
        assertAll(
                () -> assertEquals(finalProductEntity.getTitle(), optionalProductEntity.get().getTitle()),
                () -> assertEquals(finalProductEntity.getZipcode(), optionalProductEntity.get().getZipcode()),
                () -> assertEquals(finalProductEntity.getSeller(), optionalProductEntity.get().getSeller()),
                () -> assertEquals(finalProductEntity.getThumbnail(), optionalProductEntity.get().getThumbnail()),
                () -> assertEquals(finalProductEntity.getDate(), optionalProductEntity.get().getDate())
        );
    }
}
