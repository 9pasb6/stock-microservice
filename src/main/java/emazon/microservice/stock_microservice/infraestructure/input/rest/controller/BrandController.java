package emazon.microservice.stock_microservice.infraestructure.input.rest.controller;

import emazon.microservice.stock_microservice.aplication.dto.request.BrandRequest;
import emazon.microservice.stock_microservice.aplication.dto.response.BrandResponse;
import emazon.microservice.stock_microservice.aplication.handler.IBrandHandler;
import emazon.microservice.stock_microservice.domain.exceptions.BrandExceptions;
import emazon.microservice.stock_microservice.domain.util.ErrorMessages;
import emazon.microservice.stock_microservice.infraestructure.input.rest.exception.InvalidInputException;
import emazon.microservice.stock_microservice.infraestructure.input.rest.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final IBrandHandler brandHandler;

    @PostMapping
    @Operation(summary = "Create a new brand", description = "Allows an admin to create a new brand with specified details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Brand created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<Void> saveBrand(@RequestBody BrandRequest brandRequest) {
        if (brandRequest.getName() == null || brandRequest.getName().isEmpty()) {
            throw new InvalidInputException(ErrorMessages.BRAND_NAME_CANNOT_BE_NULL);
        }
        brandHandler.saveBrand(brandRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Operation(summary = "Get all brands", description = "Retrieve a list of all brands, ordered as specified.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of brands retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<BrandResponse>> getAllBrands(
            @RequestParam(defaultValue = "asc") String order) {
        List<BrandResponse> brands = brandHandler.getAllBrands(order);
        return ResponseEntity.ok(brands);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a brand by ID", description = "Retrieve details of a specific brand by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Brand not found")
    })
    public ResponseEntity<BrandResponse> getBrandById(@PathVariable Long id) {
        try {
            BrandResponse brandResponse = brandHandler.getBrand(id);
            if (brandResponse == null) {
                throw new ResourceNotFoundException(ErrorMessages.BRAND_NOT_FOUND + id + ErrorMessages.BRAND_NOT_FOUND_SUFFIX);
            }
            return ResponseEntity.ok(brandResponse);
        } catch (BrandExceptions.BrandNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @PutMapping
    @Operation(summary = "Update a brand", description = "Update the details of an existing brand.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Brand not found")
    })
    public ResponseEntity<Void> updateBrand(@RequestBody BrandRequest brandRequest) {
        if (brandRequest.getName() == null || brandRequest.getName().isEmpty()) {
            throw new InvalidInputException(ErrorMessages.BRAND_NAME_CANNOT_BE_NULL);
        }
        brandHandler.updateBrand(brandRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a brand", description = "Delete a brand by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Brand not found")
    })
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        if (!brandHandler.deleteBrand(id)) {
            throw new ResourceNotFoundException(ErrorMessages.BRAND_NOT_FOUND + id + ErrorMessages.BRAND_NOT_FOUND_SUFFIX);
        }
        return ResponseEntity.ok().build();
    }
}