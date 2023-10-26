package com.example.desafiostone.service.impl;

import com.example.desafiostone.entity.ProductEntity;
import com.example.desafiostone.mapper.ProductMapper;
import com.example.desafiostone.model.Product;
import com.example.desafiostone.repository.ProductRepository;
import com.example.desafiostone.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Override
    public Product getProduct(UUID productId) {
        return productMapper.toModel(productRepository.findById(productId).orElseThrow());
    }

    @Override
    public Product createProduct(Product product) {
        ProductEntity productEntity = productMapper.toEntity(product);
        productEntity = productRepository.save(productEntity);

        return productMapper.toModel(productEntity);
    }
}
