package com.yogi.inventorymanagement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.yogi.inventorymanagement.model.ProductType;

@DataJpaTest
@ActiveProfiles("test")
public class ProductTypeRepositoryTest {

    @Autowired
    private ProductTypeRepository repository;

    @Test
    void whenSave_thenFindsByIdAndFindAll() {
        ProductType pt = new ProductType();
        pt.setName("Grains");
        pt.setDescription("Grain products category");

        ProductType saved = repository.save(pt);

        assertThat(saved.getId()).isNotNull();

        ProductType found = repository.findById(saved.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Grains");

        List<ProductType> all = repository.findAll();
        assertThat(all).isNotEmpty();
    }
}
