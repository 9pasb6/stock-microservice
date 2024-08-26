package emazon.microservice.stock_microservice.domain.spi;

import emazon.microservice.stock_microservice.domain.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface ICategoryPersistencePort {

    Category save(Category category);

    Category findByName(String name);

    void delete(Category category);

    Category update(Category category);

    List<Category> findAll(String order);

    Category findById(Long id);

    void deleteAll();

    void deleteById(Long id);

    List<Category> getCategoriesByIds(Set<Long> ids);

    Set<String> getCategoryNamesByIds(Set<Long> ids);

    boolean existsByName(String name);

    boolean existByIds(Set<Long> ids);

}
