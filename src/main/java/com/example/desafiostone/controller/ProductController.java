package com.example.desafiostone.controller;

import com.example.desafiostone.model.Product;
import com.example.desafiostone.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/starstore/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping({"/{productId}", "/{productId}/"})
    public ResponseEntity<Product> getProduct(@PathVariable UUID productId) {
        return new ResponseEntity<>(productService.getProduct(productId), HttpStatus.OK);
    }
}
