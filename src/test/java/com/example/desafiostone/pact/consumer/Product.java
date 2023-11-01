package com.example.desafiostone.pact.consumer;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


import com.sngular.annotation.pact.Example;
import com.sngular.annotation.pact.PactDslBodyBuilder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@PactDslBodyBuilder
public class Product {

    private UUID id;
    @Example("My Product")
    private String title;
    private BigDecimal price;
    private String zipcode;
    private String seller;
    private String thumbnail;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") //todo: verify format - 30/10/2023
    private LocalDate date;
}
