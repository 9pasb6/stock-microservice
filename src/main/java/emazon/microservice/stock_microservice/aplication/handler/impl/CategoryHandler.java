package emazon.microservice.stock_microservice.aplication.handler.impl;

import emazon.microservice.stock_microservice.aplication.dto.request.CategoryRequest;
import emazon.microservice.stock_microservice.aplication.dto.response.CategoryResponse;
import emazon.microservice.stock_microservice.aplication.handler.ICategoryHandler;
import emazon.microservice.stock_microservice.aplication.mapper.request.CategoryRequestMapper;
import emazon.microservice.stock_microservice.aplication.mapper.response.CategoryResponseMapper;
import emazon.microservice.stock_microservice.domain.api.ICategoryServicePort;
import emazon.microservice.stock_microservice.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryHandler implements ICategoryHandler {

    private final ICategoryServicePort categoryServicePort;
    private final CategoryRequestMapper categoryRequestMapper;
    private final CategoryResponseMapper categoryResponseMapper;

    @Override
    public Category save(CategoryRequest categoryRequest) {
        System.out.println("categoryRequest = " + categoryRequest);
        Category category = categoryRequestMapper.requestToCategory(categoryRequest);
        return categoryServicePort.save(category);
    }

    @Override
    public CategoryResponse findByName(String name) {
        Category category = categoryServicePort.findByName(name);
        return categoryResponseMapper.categoryToResponse(category);
    }

    @Override
    public void delete(CategoryRequest categoryRequest) {
        Category category = categoryRequestMapper.requestToCategory(categoryRequest);
        categoryServicePort.delete(category);
    }

    @Override
    public Category update(CategoryRequest categoryRequest) {
        Category category = categoryRequestMapper.requestToCategory(categoryRequest);
        return categoryServicePort.update(category);
    }

    @Override
    public List<CategoryResponse> findAll() {
        List<Category> categories = categoryServicePort.findAll();
        return categories.stream()
                .map(categoryResponseMapper::categoryToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        categoryServicePort.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        categoryServicePort.deleteById(id);
    }

    @Override
    public List<CategoryResponse> getCategoriesByIds(Set<Long> ids) {
        List<Category> categories = categoryServicePort.getCategoriesByIds(ids);
        return categories.stream()
                .map(categoryResponseMapper::categoryToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> getCategoryNamesByIds(Set<Long> ids) {
        return categoryServicePort.getCategoryNamesByIds(ids);
    }
}