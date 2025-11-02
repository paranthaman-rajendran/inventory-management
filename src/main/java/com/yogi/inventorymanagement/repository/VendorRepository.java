package com.yogi.inventorymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yogi.inventorymanagement.model.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer> {

}
