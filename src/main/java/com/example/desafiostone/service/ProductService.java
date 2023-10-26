package com.example.desafiostone.service;

import com.example.desafiostone.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    Product getProduct(UUID productId);

    List<Product> getProducts();

    Product createProduct(Product product);
}
