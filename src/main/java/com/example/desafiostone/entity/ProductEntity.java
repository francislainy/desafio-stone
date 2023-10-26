package com.example.desafiostone.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Getter
@Setter
@Table(name = "products")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "seller")
    private String seller;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "date")
    private LocalDate date;
}
