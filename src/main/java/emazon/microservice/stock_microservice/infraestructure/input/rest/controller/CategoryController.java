package emazon.microservice.stock_microservice.infraestructure.input.rest.controller;


import emazon.microservice.stock_microservice.aplication.dto.request.CategoryRequest;
import emazon.microservice.stock_microservice.aplication.dto.response.CategoryResponse;
import emazon.microservice.stock_microservice.aplication.handler.ICategoryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryHandler categoryHandler;

    @PostMapping
    public ResponseEntity<Void> saveCategory(@RequestBody CategoryRequest categoryRequest) {
        categoryHandler.save(categoryRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryHandler.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{name}")
    public ResponseEntity<CategoryResponse> getCategoryByName(@PathVariable String name) {
        CategoryResponse category = categoryHandler.findByName(name);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/ids")
    public ResponseEntity<List<CategoryResponse>> getCategoriesByIds(@RequestParam Set<Long> ids) {
        List<CategoryResponse> categories = categoryHandler.getCategoriesByIds(ids);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/names")
    public ResponseEntity<Set<String>> getCategoryNamesByIds(@RequestParam Set<Long> ids) {
        Set<String> categoryNames = categoryHandler.getCategoryNamesByIds(ids);
        return ResponseEntity.ok(categoryNames);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCategory( @RequestBody CategoryRequest categoryRequest) {
        categoryHandler.update(categoryRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllCategories() {
        categoryHandler.deleteAll();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) {
        categoryHandler.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCategory(@RequestBody CategoryRequest categoryRequest) {
        categoryHandler.delete(categoryRequest);
        return ResponseEntity.ok().build();
    }
}