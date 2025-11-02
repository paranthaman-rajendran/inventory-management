package com.yogi.inventorymanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yogi.inventorymanagement.dto.VendorDto;
import com.yogi.inventorymanagement.service.VendorService;

@Controller
public class VendorWebController {

    private final VendorService vendorService;

    public VendorWebController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping({ "/", "/home", "/index" })
    public String home() {
        return "index";
    }

    @GetMapping("/vendors")
    public String vendors(Model model, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        model.addAttribute("page", vendorService.findPage(page, size));
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "vendors/list";
    }

    @GetMapping("/vendors/new")
    public String newVendor(Model model) {
        model.addAttribute("vendor", new VendorDto());
        return "vendors/form";
    }

    @PostMapping("/vendors")
    public String createVendor(@ModelAttribute VendorDto vendor) {
        vendorService.save(vendor);
        return "redirect:/vendors";
    }

    @GetMapping("/vendors/{id}/edit")
    public String editVendor(@PathVariable Integer id, Model model) {
        VendorDto dto = vendorService.findById(id).orElse(new VendorDto());
        model.addAttribute("vendor", dto);
        return "vendors/form";
    }

    @PostMapping("/vendors/{id}/update")
    public String updateVendor(@PathVariable Integer id, @ModelAttribute VendorDto vendor) {
        vendorService.update(id, vendor);
        return "redirect:/vendors";
    }

    @PostMapping("/vendors/{id}/delete")
    public String deleteVendor(@PathVariable Integer id) {
        vendorService.deleteById(id);
        return "redirect:/vendors";
    }
}
