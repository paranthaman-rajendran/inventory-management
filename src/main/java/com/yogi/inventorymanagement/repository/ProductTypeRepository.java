package com.yogi.inventorymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yogi.inventorymanagement.model.ProductType;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {

}
