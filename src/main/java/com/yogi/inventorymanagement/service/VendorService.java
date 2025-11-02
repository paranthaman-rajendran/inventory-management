package com.yogi.inventorymanagement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yogi.inventorymanagement.dto.VendorDto;
import com.yogi.inventorymanagement.model.Vendor;
import com.yogi.inventorymanagement.repository.VendorRepository;

@Service
public class VendorService {

    private final VendorRepository repository;

    public VendorService(VendorRepository repository) {
        this.repository = repository;
    }

    public List<VendorDto> findAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public Page<VendorDto> findPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Vendor> p = repository.findAll(pageable);
        return new PageImpl<>(p.stream().map(this::toDto).collect(Collectors.toList()), pageable, p.getTotalElements());
    }

    public Optional<VendorDto> findById(Integer id) {
        return repository.findById(id).map(this::toDto);
    }

    public VendorDto save(VendorDto dto) {
        Vendor v = fromDto(dto);
        LocalDateTime now = LocalDateTime.now();
        v.setCreatedAt(now);
        v.setUpdatedAt(now);
        Vendor saved = repository.save(v);
        return toDto(saved);
    }

    public Optional<VendorDto> update(Integer id, VendorDto dto) {
        return repository.findById(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setContactNo(dto.getContactNo());
            existing.setAddress(dto.getAddress());
            existing.setUpdatedAt(LocalDateTime.now());
            Vendor saved = repository.save(existing);
            return toDto(saved);
        });
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    private VendorDto toDto(Vendor v) {
        return new VendorDto(v.getVendorId(), v.getName(), v.getContactNo(), v.getAddress(), v.getCreatedAt(),
                v.getUpdatedAt());
    }

    private Vendor fromDto(VendorDto dto) {
        Vendor v = new Vendor();
        v.setName(dto.getName());
        v.setContactNo(dto.getContactNo());
        v.setAddress(dto.getAddress());
        return v;
    }
}
