package com.example.desafiostone.repository;

import com.example.desafiostone.entity.CardEntity;
import com.example.desafiostone.entity.ClientEntity;
import com.example.desafiostone.entity.TransactionEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDate.now;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TransactionRepositoryTest {

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    void createTransactionBuyProduct() {
        ClientEntity clientEntity = ClientEntity.builder()
                .id(randomUUID())
                .name("anyClient")
                .build();
        CardEntity cardEntity = CardEntity.builder()
                .id(randomUUID())
                .cardNumber("1111-1111-1111-1111")
                .value(7990)
                .cvv(789)
                .cardHolderName("any name")
                .expDate(now())
                .build();

        TransactionEntity transactionEntity = TransactionEntity.builder()
                .id(randomUUID())
                .totalToPay(BigDecimal.TEN)
                .clientEntity(clientEntity)
                .cardEntity(cardEntity)
                .build();

        transactionEntity = transactionRepository.save(transactionEntity);

        Optional<TransactionEntity> optionalTransactionEntity = transactionRepository.findById(transactionEntity.getId());

        assertTrue(optionalTransactionEntity.isPresent());

        TransactionEntity finalTransactionEntity = transactionEntity;
        assertAll(
                () -> assertEquals(finalTransactionEntity.getCardEntity(), optionalTransactionEntity.get().getCardEntity()),
                () -> assertEquals(finalTransactionEntity.getClientEntity(), optionalTransactionEntity.get().getClientEntity()),
                () -> assertEquals(finalTransactionEntity.getTotalToPay(), optionalTransactionEntity.get().getTotalToPay()),
                () -> assertEquals(finalTransactionEntity.getId(), optionalTransactionEntity.get().getId())
        );
    }

    @Test
    void getHistory() {
        ClientEntity clientEntity = ClientEntity.builder()
                .name("anyClient")
                .build();
        CardEntity cardEntity = CardEntity.builder()
                .cardNumber("1111-1111-1111-1111")
                .value(7990)
                .cvv(789)
                .cardHolderName("any name")
                .expDate(now())
                .build();

        TransactionEntity transactionEntity = TransactionEntity.builder()
                .totalToPay(BigDecimal.TEN)
                .clientEntity(clientEntity)
                .cardEntity(cardEntity)
                .build();

        transactionEntity = transactionRepository.save(transactionEntity);

        List<TransactionEntity> transactionEntityList = transactionRepository.findAll();

        TransactionEntity actualTransactionEntity = transactionEntityList.get(0);

        TransactionEntity finalTransactionEntity = transactionEntity;
        assertAll(
                () -> assertEquals(finalTransactionEntity.getCardEntity(), actualTransactionEntity.getCardEntity()),
                () -> assertEquals(finalTransactionEntity.getClientEntity(), actualTransactionEntity.getClientEntity()),
                () -> assertEquals(finalTransactionEntity.getTotalToPay(), actualTransactionEntity.getTotalToPay()),
                () -> assertEquals(finalTransactionEntity.getId(), actualTransactionEntity.getId())
        );
    }

    @Test
    void getHistoryForClient() {
        ClientEntity clientEntity = ClientEntity.builder()
                .id(randomUUID())
                .name("anyClient")
                .build();
        CardEntity cardEntity = CardEntity.builder()
                .id(randomUUID())
                .cardNumber("1111-1111-1111-1111")
                .value(7990)
                .cvv(789)
                .cardHolderName("any name")
                .expDate(now())
                .build();

        TransactionEntity transactionEntity = TransactionEntity.builder()
                .id(randomUUID())
                .totalToPay(BigDecimal.TEN)
                .clientEntity(clientEntity)
                .cardEntity(cardEntity)
                .build();

        transactionEntity = transactionRepository.save(transactionEntity);

        List<TransactionEntity> transactionEntityList = transactionRepository.findByClientEntityId(clientEntity.getId());

        TransactionEntity actualTransactionEntity = transactionEntityList.get(0);

        TransactionEntity finalTransactionEntity = transactionEntity;
        assertAll(
                () -> assertEquals(finalTransactionEntity.getCardEntity(), actualTransactionEntity.getCardEntity()),
                () -> assertEquals(finalTransactionEntity.getClientEntity(), actualTransactionEntity.getClientEntity()),
                () -> assertEquals(finalTransactionEntity.getTotalToPay(), actualTransactionEntity.getTotalToPay()),
                () -> assertEquals(finalTransactionEntity.getId(), actualTransactionEntity.getId())
        );
    }
}
