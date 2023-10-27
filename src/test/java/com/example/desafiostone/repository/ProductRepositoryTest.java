package com.example.desafiostone.repository;

import com.example.desafiostone.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
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
                .price(BigDecimal.TEN)
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
                () -> assertEquals(finalProductEntity.getPrice(), optionalProductEntity.get().getPrice()),
                () -> assertEquals(finalProductEntity.getZipcode(), optionalProductEntity.get().getZipcode()),
                () -> assertEquals(finalProductEntity.getSeller(), optionalProductEntity.get().getSeller()),
                () -> assertEquals(finalProductEntity.getThumbnail(), optionalProductEntity.get().getThumbnail()),
                () -> assertEquals(finalProductEntity.getDate(), optionalProductEntity.get().getDate())
        );
    }

    @Test
    void getProducts() {
        ProductEntity productEntity = ProductEntity.builder()
                .price(BigDecimal.TEN)
                .title("anyProduct")
                .zipcode("78993-000")
                .seller("João da Silva")
                .thumbnail("https://cdn.awsli.com.br/600x450/21/21351/produto/3853007/f66e8c63ab.jpg")
                .date(now())
                .build();

        productEntity = productRepository.save(productEntity);

        List<ProductEntity> productEntityList = productRepository.findAll();

        assertFalse(productEntityList.isEmpty());

        ProductEntity finalProductEntity = productEntity;
        assertAll(
                () -> assertEquals(finalProductEntity.getTitle(), productEntityList.get(0).getTitle()),
                () -> assertEquals(finalProductEntity.getPrice(), productEntityList.get(0).getPrice()),
                () -> assertEquals(finalProductEntity.getZipcode(), productEntityList.get(0).getZipcode()),
                () -> assertEquals(finalProductEntity.getSeller(), productEntityList.get(0).getSeller()),
                () -> assertEquals(finalProductEntity.getThumbnail(), productEntityList.get(0).getThumbnail()),
                () -> assertEquals(finalProductEntity.getDate(), productEntityList.get(0).getDate())
        );
    }
}
