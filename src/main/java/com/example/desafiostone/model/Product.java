package com.example.desafiostone.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Product {

    private UUID id;
    private String title;

    private double price; //todo: BigDecimal
    private String zipcode;
    private String seller;
    private String thumbnailHd; //todo: url
    private String date; //todo: date
}
