package com.ecommerce.controller;

import com.ecommerce.dto.request.BrandRequest;
import com.ecommerce.dto.response.BrandResponse;
import com.ecommerce.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    public BrandResponse createBrand(@Valid @RequestBody BrandRequest request) {
        return brandService.createBrand(request);
    }

    @PutMapping("/{brandId}")
    public BrandResponse updateBrand(@PathVariable Long brandId,
                                     @Valid @RequestBody BrandRequest request) {
        return brandService.updateBrand(brandId, request);
    }

    @DeleteMapping("/{brandId}")
    public void deleteBrand(@PathVariable Long brandId) {
        brandService.deleteBrand(brandId);
    }

    @GetMapping("/{brandId}")
    public BrandResponse getBrandById(@PathVariable Long brandId) {
        return brandService.getBrandById(brandId);
    }

    @GetMapping
    public List<BrandResponse> getAllBrands() {
        return brandService.getAllBrands();
    }
}