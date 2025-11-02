package com.yogi.inventorymanagement.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.yogi.inventorymanagement.dto.ProductTypeDto;
import com.yogi.inventorymanagement.service.ProductTypeService;

@WebMvcTest(ProductTypeController.class)
class ProductTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductTypeService service;

    @Test
    void listAll_ShouldReturnAllProductTypes() throws Exception {
        // Given
        LocalDateTime now = LocalDateTime.now();
        ProductTypeDto cement = new ProductTypeDto(1, "Cement", "Various types of cement including OPC and PPC", now,
                now);
        ProductTypeDto steel = new ProductTypeDto(2, "Steel Bars", "TMT and reinforcement steel bars", now, now);

        given(service.findAll()).willReturn(Arrays.asList(cement, steel));

        // When/Then
        mockMvc.perform(get("/api/product-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Cement")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Steel Bars")));
    }

    @Test
    void getById_WhenExists_ShouldReturnProductType() throws Exception {
        // Given
        LocalDateTime now = LocalDateTime.now();
        ProductTypeDto cement = new ProductTypeDto(1, "Cement", "Various types of cement including OPC and PPC", now,
                now);
        given(service.findById(1)).willReturn(Optional.of(cement));

        // When/Then
        mockMvc.perform(get("/api/product-types/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Cement")))
                .andExpect(jsonPath("$.description", is("Various types of cement including OPC and PPC")));
    }

    @Test
    void getById_WhenNotExists_ShouldReturn404() throws Exception {
        // Given
        given(service.findById(999)).willReturn(Optional.empty());

        // When/Then
        mockMvc.perform(get("/api/product-types/999"))
                .andExpect(status().isNotFound());
    }
}