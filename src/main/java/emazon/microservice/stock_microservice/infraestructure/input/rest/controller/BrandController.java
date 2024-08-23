package emazon.microservice.stock_microservice.infraestructure.input.rest.controller;

import emazon.microservice.stock_microservice.aplication.dto.request.BrandRequest;
import emazon.microservice.stock_microservice.aplication.dto.response.BrandResponse;
import emazon.microservice.stock_microservice.aplication.handler.IBrandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final IBrandHandler brandHandler;

    @PostMapping
    public ResponseEntity<Void> saveBrand(@RequestBody BrandRequest brandRequest) {
        brandHandler.saveBrand(brandRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<BrandResponse>> getAllBrands(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        Page<BrandResponse> brands = brandHandler.getAllBrands(pageable);

        return ResponseEntity.ok(brands);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandResponse> getBrandById(@PathVariable Long id) {
        BrandResponse brand = brandHandler.getBrand(id);
        return ResponseEntity.ok(brand);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBrand( @RequestBody BrandRequest brandRequest) {

        brandHandler.updateBrand(brandRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrandById(@PathVariable Long id) {
        brandHandler.deleteBrand(id);
        return ResponseEntity.ok().build();
    }
}