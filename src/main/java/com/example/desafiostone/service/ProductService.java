package com.example.desafiostone.service;

import com.example.desafiostone.model.Product;

import java.util.UUID;

public interface ProductService {

    Product getProduct(UUID productId);

    Product createProduct(Product product);
}
