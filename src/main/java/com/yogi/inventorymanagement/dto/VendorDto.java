package com.yogi.inventorymanagement.dto;

import java.time.LocalDateTime;

public class VendorDto {

    private Integer vendorId;
    private String name;
    private String contactNo;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public VendorDto() {
    }

    public VendorDto(Integer vendorId, String name, String contactNo, String address, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.vendorId = vendorId;
        this.name = name;
        this.contactNo = contactNo;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
