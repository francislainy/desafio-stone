package com.example.desafiostone.service.impl;

import com.example.desafiostone.entity.ProductEntity;
import com.example.desafiostone.entity.TransactionEntity;
import com.example.desafiostone.mapper.ProductMapper;
import com.example.desafiostone.mapper.TransactionMapper;
import com.example.desafiostone.model.HistoryItem;
import com.example.desafiostone.model.Product;
import com.example.desafiostone.model.Transaction;
import com.example.desafiostone.repository.ProductRepository;
import com.example.desafiostone.service.ProductService;
import com.example.desafiostone.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final TransactionRepository transactionRepository;

    private final ProductMapper productMapper;
    private final TransactionMapper transactionMapper;

    @Override
    public Product getProduct(UUID productId) {
        return productMapper.toModel(productRepository.findById(productId).orElseThrow());
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toModel)
                .toList();
    }

    @Override
    public Product createProduct(Product product) {
        ProductEntity productEntity = productMapper.toEntity(product);
        productEntity = productRepository.save(productEntity);

        return productMapper.toModel(productEntity);
    }

    @Override
    public Transaction buyProduct(Transaction transaction) {
        TransactionEntity transactionEntity = transactionMapper.toEntity(transaction);
        transactionEntity = transactionRepository.save(transactionEntity);

        return transactionMapper.toModel(transactionEntity);
    }

    @Override
    public List<HistoryItem> getHistory() {
        return transactionRepository.findAll().stream()
                .map(t -> HistoryItem.builder()
                        .clientId(t.getClientEntity().getId())
                        .purchaseId(t.getId())
                        .value(t.getTotalToPay())
                        .date(t.getCardEntity().getExpDate())
                        .cardNumber(t.getCardEntity().getCardNumber())
                        .build())
                .toList();
    }

    @Override
    public List<HistoryItem> getHistoryForClient(UUID clientId) {
        return transactionRepository.findByClientEntityId(clientId).stream()
                .map(t -> HistoryItem.builder()
                        .clientId(t.getClientEntity().getId())
                        .purchaseId(t.getId())
                        .value(t.getTotalToPay())
                        .date(t.getCardEntity().getExpDate())
                        .cardNumber(t.getCardEntity().getCardNumber())
                        .build())
                .toList();
    }
}
