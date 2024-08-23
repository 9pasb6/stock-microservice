package emazon.microservice.stock_microservice.domain.api;

import emazon.microservice.stock_microservice.domain.model.Category;

import java.util.List;
import java.util.Set;

public interface ICategoryServicePort {

    Category save(Category category);
    Category findByName(String name);
    void delete(Category category);
    Category update(Category category);
    List<Category> findAll();
    void deleteAll();
    void deleteById(Long id);
    // Método para obtener categorías por sus IDs
    List<Category> getCategoriesByIds(Set<Long> ids);

    // Método para obtener nombres de categorías por sus IDs
    Set<String> getCategoryNamesByIds(Set<Long> ids); // Devuelve un Set<String>
}
