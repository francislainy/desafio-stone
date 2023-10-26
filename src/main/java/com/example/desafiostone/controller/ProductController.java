package com.example.desafiostone.controller;

import com.example.desafiostone.model.Product;
import com.example.desafiostone.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/starstore")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping({"/product/{productId}", "/product/{productId}/"})
    public ResponseEntity<Product> getProduct(@PathVariable UUID productId) {
        return new ResponseEntity<>(productService.getProduct(productId), HttpStatus.OK);
    }

    @GetMapping({"/products", "/products/"})
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    @PostMapping({"/product", "/product/"})
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
    }

}
