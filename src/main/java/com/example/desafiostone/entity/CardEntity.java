package com.example.desafiostone.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Getter
@Setter
@Table(name = "cards")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CardEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "card_holder_name")
    private String cardHolderName;

    @Column(name = "value")
    private int value;

    @Column(name = "cvv")
    private int cvv;

    @Column(name = "exp_date")
    private LocalDate expDate;

    @OneToOne(mappedBy = "cardEntity")
    private TransactionEntity transactionEntity;
}
