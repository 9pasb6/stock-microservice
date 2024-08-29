package emazon.microservice.stock_microservice.infraestructure.input.rest.controller;

import emazon.microservice.stock_microservice.aplication.dto.request.CategoryRequest;
import emazon.microservice.stock_microservice.aplication.dto.response.CategoryResponse;
import emazon.microservice.stock_microservice.aplication.handler.ICategoryHandler;
import emazon.microservice.stock_microservice.domain.exceptions.CategoryExceptions;
import emazon.microservice.stock_microservice.domain.util.ErrorMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryHandler categoryHandler;

    @PostMapping
    @Operation(summary = "Create a new category", description = "Allows an admin to create a new category with specified details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<Void> saveCategory(@RequestBody CategoryRequest categoryRequest) {
        if (categoryRequest.getName() == null || categoryRequest.getName().isEmpty()) {
            throw new IllegalArgumentException(ErrorMessages.CATEGORY_NAME_CANNOT_BE_NULL);
        }
        categoryHandler.save(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Operation(summary = "Get all categories", description = "Retrieve a list of all categories, ordered as specified.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of categories retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<CategoryResponse>> getAllCategories(@RequestParam(defaultValue = "asc") String order) {
        List<CategoryResponse> categories = categoryHandler.findAll(order);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{name}")
    @Operation(summary = "Get category by name", description = "Retrieve details of a specific category by its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<CategoryResponse> getCategoryByName(@PathVariable String name) {
        CategoryResponse categoryResponse = categoryHandler.findByName(name);
        return ResponseEntity.ok(categoryResponse);
    }

    @GetMapping("/ids")
    @Operation(summary = "Get categories by IDs", description = "Retrieve a list of categories based on their IDs.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of categories retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Categories not found")
    })
    public ResponseEntity<List<CategoryResponse>> getCategoriesByIds(@RequestParam Set<Long> ids) {
        try {
            List<CategoryResponse> categories = categoryHandler.getCategoriesByIds(ids);
            return ResponseEntity.ok(categories);
        } catch (CategoryExceptions.CategoryNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.emptyList());
        }
    }

    @GetMapping("/names")
    @Operation(summary = "Get category names by IDs", description = "Retrieve the names of categories based on their IDs.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category names retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Categories not found")
    })
    public ResponseEntity<Set<String>> getCategoryNamesByIds(@RequestParam Set<Long> ids) {
        Set<String> categoryNames = categoryHandler.getCategoryNamesByIds(ids);
        return ResponseEntity.ok(categoryNames);
    }

    @PutMapping
    @Operation(summary = "Update a category", description = "Update the details of an existing category.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<Void> updateCategory(@RequestBody CategoryRequest categoryRequest) {
        if (categoryRequest.getName() == null || categoryRequest.getName().isEmpty()) {
            throw new IllegalArgumentException(ErrorMessages.CATEGORY_NAME_CANNOT_BE_NULL);
        }
        categoryHandler.update(categoryRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @Operation(summary = "Delete all categories", description = "Delete all categories from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All categories deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteAllCategories() {
        categoryHandler.deleteAll();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category by ID", description = "Delete a category by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) {
        if (!categoryHandler.deleteById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().build();
    }

}