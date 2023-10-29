package com.example.desafiostone.mapper;

import com.example.desafiostone.entity.TransactionEntity;
import com.example.desafiostone.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper
public interface TransactionMapper {

    @Mapping(target = "card.expDate", expression = "java(convertLocalDateToString(cardEntity.getExpDate()))")
    @Mapping(source = "cardEntity.id", target = "card.id")
    @Mapping(source = "clientEntity.id", target = "client.id")
    Transaction toModel(TransactionEntity transactionEntity);

    @Mapping(target = "cardEntity.expDate", expression = "java(convertStringToLocalDate(card.getExpDate()))")
    @Mapping(target = "cardEntity.id", source = "card.id")
    @Mapping(target = "clientEntity.id", source = "client.id")
    TransactionEntity toEntity(Transaction transaction);

    default String convertLocalDateToString(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("MM/dd"));
    }

    default LocalDate convertStringToLocalDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return LocalDate.parse(dateStr + "/" + LocalDate.now().getYear(), formatter);
    }
}

