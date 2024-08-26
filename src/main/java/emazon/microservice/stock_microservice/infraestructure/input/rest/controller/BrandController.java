package emazon.microservice.stock_microservice.infraestructure.input.rest.controller;

import emazon.microservice.stock_microservice.aplication.dto.request.BrandRequest;
import emazon.microservice.stock_microservice.aplication.dto.response.BrandResponse;
import emazon.microservice.stock_microservice.aplication.handler.IBrandHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final IBrandHandler brandHandler;

    @Operation(summary = "Create a new brand", description = "Allows an admin to create a new brand with specified details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Void> saveBrand(@RequestBody BrandRequest brandRequest) {
        brandHandler.saveBrand(brandRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get all brands", description = "Retrieve a list of all brands, ordered as specified.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of brands retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<BrandResponse>> getAllBrands(
            @RequestParam(defaultValue = "asc") String order) {
        List<BrandResponse> brands = brandHandler.getAllBrands(order);
        return ResponseEntity.ok(brands);
    }

    @Operation(summary = "Get a brand by ID", description = "Retrieve details of a specific brand by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Brand not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BrandResponse> getBrandById(@PathVariable Long id) {
        BrandResponse brand = brandHandler.getBrand(id);
        return ResponseEntity.ok(brand);
    }

    @Operation(summary = "Update a brand", description = "Update the details of an existing brand.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBrand(@RequestBody BrandRequest brandRequest) {
        brandHandler.updateBrand(brandRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete a brand by ID", description = "Delete a brand by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Brand not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrandById(@PathVariable Long id) {
        brandHandler.deleteBrand(id);
        return ResponseEntity.ok().build();
    }
}