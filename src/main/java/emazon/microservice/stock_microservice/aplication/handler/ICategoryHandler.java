package emazon.microservice.stock_microservice.aplication.handler;

import emazon.microservice.stock_microservice.aplication.dto.request.CategoryRequest;
import emazon.microservice.stock_microservice.aplication.dto.response.CategoryResponse;
import emazon.microservice.stock_microservice.domain.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface ICategoryHandler {

    Category save(CategoryRequest categoryRequest);

    CategoryResponse findByName(String name);

    void delete(CategoryRequest categoryRequest);

    Category update(CategoryRequest categoryRequest);

    List<CategoryResponse> findAll(String order);

    void deleteAll();

    void deleteById(Long id);

    List<CategoryResponse> getCategoriesByIds(Set<Long> ids);

    Set<String> getCategoryNamesByIds(Set<Long> ids);
}