package com.example.desafiostone.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private UUID id;
    private String title;
    private BigDecimal price;
    private String zipcode;
    private String seller;
    private String thumbnail;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") //todo: verify format - 30/10/2023
    private LocalDate date;
}
