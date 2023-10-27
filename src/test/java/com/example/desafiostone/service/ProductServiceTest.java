package com.example.desafiostone.service;

import com.example.desafiostone.entity.CardEntity;
import com.example.desafiostone.entity.ClientEntity;
import com.example.desafiostone.entity.ProductEntity;
import com.example.desafiostone.entity.TransactionEntity;
import com.example.desafiostone.mapper.ProductMapper;
import com.example.desafiostone.mapper.TransactionMapper;
import com.example.desafiostone.model.Product;
import com.example.desafiostone.model.Transaction;
import com.example.desafiostone.repository.ProductRepository;
import com.example.desafiostone.repository.TransactionRepository;
import com.example.desafiostone.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.desafiostone.utils.Util.convertToNewObject;
import static java.time.LocalDate.now;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductServiceTest {

    @InjectMocks
    ProductServiceImpl productService;

    ProductRepository productRepository;
    TransactionRepository transactionRepository;

    @Spy
    ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Spy
    TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);

    UUID productId = randomUUID();
    LocalDate date;

    ProductEntity expectedProductEntity;

    ProductEntity requestProductEntity;

    @BeforeAll
    void setUp() {
        productRepository = mock(ProductRepository.class);
        transactionRepository = mock(TransactionRepository.class);
        date = now();

        requestProductEntity = ProductEntity.builder()
                .title("Blusa do Imperio")
                .price(BigDecimal.TEN)
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
                () -> assertEquals(expectedProduct.getPrice(), actualProduct.getPrice()),
                () -> assertEquals(expectedProduct.getZipcode(), actualProduct.getZipcode()),
                () -> assertEquals(expectedProduct.getSeller(), actualProduct.getSeller()),
                () -> assertEquals(expectedProduct.getThumbnail(), actualProduct.getThumbnail()),
                () -> assertEquals(expectedProduct.getDate(), actualProduct.getDate())
        );
    }

    @Test
    void getProducts() {
        Product expectedProduct = productMapper.toModel(expectedProductEntity);

        when(productRepository.findAll()).thenReturn(List.of(expectedProductEntity));
        when(productMapper.toModel(any())).thenReturn(expectedProduct);

        List<Product> productList = productService.getProducts();

        Product actualProduct = productList.get(0);

        assertNotNull(actualProduct);
        assertAll(
                () -> assertEquals(expectedProduct.getTitle(), actualProduct.getTitle()),
                () -> assertEquals(expectedProduct.getPrice(), actualProduct.getPrice()),
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
                () -> assertEquals(expectedProduct.getPrice(), actualProduct.getPrice()),
                () -> assertEquals(expectedProduct.getSeller(), actualProduct.getSeller()),
                () -> assertEquals(expectedProduct.getThumbnail(), actualProduct.getThumbnail()),
                () -> assertEquals(expectedProduct.getDate(), actualProduct.getDate())
        );
    }

    @Test
    void buyProduct() {
        TransactionEntity transactionEntity = TransactionEntity.builder()
                .id(randomUUID())
                .totalToPay(BigDecimal.TEN)
                .clientEntity(ClientEntity.builder()
                        .id(randomUUID())
                        .name("anyClient")
                        .build())
                .cardEntity(CardEntity.builder()
                        .id(randomUUID())
                        .cardNumber("1111-1111-1111-1111")
                        .value(7990)
                        .cvv(789)
                        //.cardHolderName(); //todo: card holder - 27/10/2023
                        .expDate(now()) //todo: format for date - 27/10/2023
                        .build())
                .build();

        when(transactionRepository.save(any())).thenReturn(transactionEntity);

        Transaction transaction = transactionMapper.toModel(transactionEntity);

        Transaction actualTransaction = productService.buyProduct(transaction);
        assertAll(
                () -> assertEquals(transaction.getClient().getId(), actualTransaction.getClient().getId()),
                () -> assertEquals(transaction.getClient().getName(), actualTransaction.getClient().getName()),
                () -> assertEquals(transaction.getTotalToPay(), actualTransaction.getTotalToPay()),
                () -> assertEquals(transaction.getCard().getId(), actualTransaction.getCard().getId()),
                () -> assertEquals(transaction.getCard().getCardNumber(), actualTransaction.getCard().getCardNumber()),
                () -> assertEquals(transaction.getCard().getValue(), actualTransaction.getCard().getValue()),
                () -> assertEquals(transaction.getCard().getCvv(), actualTransaction.getCard().getCvv()),
                () -> assertEquals(transaction.getCard().getCardHolderName(), actualTransaction.getCard().getCardHolderName()),
                () -> assertEquals(transaction.getCard().getExpDate(), actualTransaction.getCard().getExpDate())
        );
    }
}
