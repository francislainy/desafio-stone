package com.example.desafiostone.mapper;

import com.example.desafiostone.entity.ProductEntity;
import com.example.desafiostone.model.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

    Product toModel(ProductEntity productEntity);

    ProductEntity toEntity(Product product);
}
