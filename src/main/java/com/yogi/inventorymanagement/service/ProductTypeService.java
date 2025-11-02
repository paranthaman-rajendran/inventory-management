package com.yogi.inventorymanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.yogi.inventorymanagement.dto.ProductTypeDto;
import com.yogi.inventorymanagement.model.ProductType;
import com.yogi.inventorymanagement.repository.ProductTypeRepository;

@Service
public class ProductTypeService {

    private final ProductTypeRepository repository;

    public ProductTypeService(ProductTypeRepository repository) {
        this.repository = repository;
    }

    public List<ProductTypeDto> findAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public Optional<ProductTypeDto> findById(Integer id) {
        return repository.findById(id).map(this::toDto);
    }

    private ProductTypeDto toDto(ProductType p) {
        return new ProductTypeDto(p.getId(), p.getName(), p.getDescription(), p.getCreatedAt(), p.getUpdatedAt());
    }
}
