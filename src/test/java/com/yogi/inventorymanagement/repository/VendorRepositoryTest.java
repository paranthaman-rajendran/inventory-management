package com.yogi.inventorymanagement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.yogi.inventorymanagement.model.Vendor;

@DataJpaTest
@ActiveProfiles("test")
public class VendorRepositoryTest {

    @Autowired
    private VendorRepository repository;

    @Test
    void whenSave_thenFindsByIdAndFindAll() {
        Vendor v = new Vendor();
        v.setName("Acme Supplies");
        v.setContactNo("+1-555-0100");
        v.setAddress("123 Industrial Park");

        Vendor saved = repository.save(v);

        assertThat(saved.getVendorId()).isNotNull();

        Vendor found = repository.findById(saved.getVendorId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Acme Supplies");

        List<Vendor> all = repository.findAll();
        assertThat(all).isNotEmpty();
    }
}
