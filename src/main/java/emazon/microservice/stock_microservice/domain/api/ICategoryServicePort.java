package emazon.microservice.stock_microservice.domain.api;

import emazon.microservice.stock_microservice.domain.model.Category;


import java.util.List;
import java.util.Set;

public interface ICategoryServicePort {

    Category save(Category category);

    Category findByName(String name);

    Category update(Category category);

    List<Category> findAll(String order);

    void deleteAll();

    void deleteById(Long id);

    List<Category> getCategoriesByIds(Set<Long> ids);

    Set<String> getCategoryNamesByIds(Set<Long> ids);
}
