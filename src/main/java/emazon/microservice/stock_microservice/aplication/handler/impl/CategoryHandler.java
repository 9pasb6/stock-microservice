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
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryHandler implements ICategoryHandler {

    private final ICategoryServicePort categoryServicePort;
    private final CategoryRequestMapper categoryRequestMapper;
    private final CategoryResponseMapper categoryResponseMapper;

    @Override
    public Category save(CategoryRequest categoryRequest) {
        Category category = categoryRequestMapper.requestToCategory(categoryRequest);
        return categoryServicePort.save(category);
    }

    @Override
    public CategoryResponse findByName(String name) {
        Category category = categoryServicePort.findByName(name);
        return categoryResponseMapper.categoryToResponse(category);
    }

    @Override
    public Category update(CategoryRequest categoryRequest) {
        Category category = categoryRequestMapper.requestToCategory(categoryRequest);
        return categoryServicePort.update(category);
    }

    @Override
    public List<CategoryResponse> findAll(String order) {
        List<Category> categories = categoryServicePort.findAll(order);
        return categories.stream()
                .map(categoryResponseMapper::categoryToResponse)
                .toList();
    }

    @Override
    public void deleteAll() {
        categoryServicePort.deleteAll();
    }

    @Override
    public boolean deleteById(Long id) {
        categoryServicePort.deleteById(id);
        return false;
    }

    @Override
    public List<CategoryResponse> getCategoriesByIds(Set<Long> ids) {
        List<Category> categories = categoryServicePort.getCategoriesByIds(ids);
        return categories.stream()
                .map(categoryResponseMapper::categoryToResponse)
                .toList();
    }

    @Override
    public Set<String> getCategoryNamesByIds(Set<Long> ids) {
        return categoryServicePort.getCategoryNamesByIds(ids);
    }
}