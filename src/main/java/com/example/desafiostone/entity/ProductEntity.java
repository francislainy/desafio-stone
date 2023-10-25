package com.example.desafiostone.entity;

import jakarta.persistence.*;
import lombok.*;

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
}
