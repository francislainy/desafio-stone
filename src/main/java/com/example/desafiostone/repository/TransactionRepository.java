package com.example.desafiostone.repository;

import com.example.desafiostone.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {

    List<TransactionEntity> findByClientEntityId(UUID clientId);
}
