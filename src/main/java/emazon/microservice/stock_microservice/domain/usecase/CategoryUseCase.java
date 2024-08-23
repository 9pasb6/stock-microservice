package emazon.microservice.stock_microservice.domain.usecase;

import emazon.microservice.stock_microservice.domain.api.ICategoryServicePort;
import emazon.microservice.stock_microservice.domain.model.Brand;
import emazon.microservice.stock_microservice.domain.model.Category;
import emazon.microservice.stock_microservice.domain.spi.ICategoryPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public Category save(Category category) {
        validateCategory(category); // Validar la categoría antes de guardar

        // Verificar si ya existe una categoría con el mismo nombre
        if (categoryPersistencePort.existsByName(category.getName())) {
            throw new IllegalArgumentException("Category name already exists");
        }

        return categoryPersistencePort.save(category);
    }

    @Override
    public Category findByName(String name) {
        return categoryPersistencePort.findByName(name);
    }

    @Override
    public void delete(Category category) {
        categoryPersistencePort.delete(category);
    }

    @Override
    public Category update(Category category) {
        validateCategory(category); // Validar la categoría antes de actualizar

        // Verificar si el nombre de la categoría se está cambiando y si ya existe otra categoría con el mismo nombre
        Category existingCategory = categoryPersistencePort.findById(category.getId());
        if (existingCategory != null && !Objects.equals(existingCategory.getName(), category.getName()) &&
                categoryPersistencePort.existsByName(category.getName())) {
            throw new IllegalArgumentException("Category name already exists");
        }

        return categoryPersistencePort.update(category);
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {

        Page<Category> categories = this.categoryPersistencePort.findAll(pageable);


        return categories;
    }

    @Override
    public void deleteAll() {
        categoryPersistencePort.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        categoryPersistencePort.deleteById(id);
    }

    @Override
    public List<Category> getCategoriesByIds(Set<Long> ids) {
        return categoryPersistencePort.getCategoriesByIds(ids);
    }

    @Override
    public Set<String> getCategoryNamesByIds(Set<Long> ids) {
        return categoryPersistencePort.getCategoryNamesByIds(ids);
    }

    // Método para validar la categoría
    private void validateCategory(Category category) {
        if (category.getName() == null || category.getName().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
        if (category.getDescription() == null || category.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Brand description cannot be null or empty");
        }
        if (category.getName().length() > 50) {
            throw new IllegalArgumentException("Category name must be at most 50 characters long");
        }
        if (category.getDescription().length() > 90) {
            throw new IllegalArgumentException("Brand description must be at most 90 characters long");
        }
    }
}