package com.example.desafiostone.mapper;

import com.example.desafiostone.entity.TransactionEntity;
import com.example.desafiostone.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TransactionMapper {

    @Mapping(source = "cardEntity.id", target = "card.id")
    @Mapping(source = "clientEntity.id", target = "client.id")
    Transaction toModel(TransactionEntity transactionEntity);

    @Mapping(target = "cardEntity.id", source = "card.id")
    @Mapping(target = "clientEntity.id", source = "client.id")
    TransactionEntity toEntity(Transaction transaction);
}
