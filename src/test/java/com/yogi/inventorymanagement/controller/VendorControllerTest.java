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

import com.yogi.inventorymanagement.dto.VendorDto;
import com.yogi.inventorymanagement.service.VendorService;

@WebMvcTest(VendorController.class)
class VendorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VendorService service;

    @Test
    void listAll_ShouldReturnAllVendors() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        VendorDto v1 = new VendorDto(1, "Acme Supplies", "+1-555-0100", "123 Industrial Park", now, now);
        VendorDto v2 = new VendorDto(2, "Builders Co", "+1-555-0200", "456 Construction Ave", now, now);

        given(service.findAll()).willReturn(Arrays.asList(v1, v2));

        mockMvc.perform(get("/api/vendors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].vendorId", is(1)))
                .andExpect(jsonPath("$[0].name", is("Acme Supplies")));
    }

    @Test
    void getById_WhenExists_ShouldReturnVendor() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        VendorDto v = new VendorDto(1, "Acme Supplies", "+1-555-0100", "123 Industrial Park", now, now);
        given(service.findById(1)).willReturn(Optional.of(v));

        mockMvc.perform(get("/api/vendors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendorId", is(1)))
                .andExpect(jsonPath("$.name", is("Acme Supplies")));
    }

    @Test
    void getById_WhenNotExists_ShouldReturn404() throws Exception {
        given(service.findById(999)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/vendors/999"))
                .andExpect(status().isNotFound());
    }
}
