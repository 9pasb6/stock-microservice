package emazon.microservice.stock_microservice.domain.usecase;

import emazon.microservice.stock_microservice.domain.api.ICategoryServicePort;
import emazon.microservice.stock_microservice.domain.exceptions.CategoryExceptions;
import emazon.microservice.stock_microservice.domain.model.Category;
import emazon.microservice.stock_microservice.domain.spi.ICategoryPersistencePort;
import emazon.microservice.stock_microservice.domain.util.ValidationUtils;

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
        ValidationUtils.validateCategory(category);

        if (categoryPersistencePort.existsByName(category.getName())) {
            throw new CategoryExceptions.CategoryNameAlreadyExistsException("El nombre de la categoría ya existe");
        }

        return categoryPersistencePort.save(category);
    }

    @Override
    public Category findByName(String name) {
        Category category = categoryPersistencePort.findByName(name);
        if (category == null) {
            throw new CategoryExceptions.CategoryNotFoundException("La categoría con nombre '" + name + "' no se encuentra");
        }
        return category;
    }

    @Override
    public void delete(Category category) {
        categoryPersistencePort.delete(category);
    }

    @Override
    public Category update(Category category) {
        ValidationUtils.validateCategory(category);

        Category existingCategory = categoryPersistencePort.findById(category.getId());
        if (existingCategory != null && !Objects.equals(existingCategory.getName(), category.getName()) &&
                categoryPersistencePort.existsByName(category.getName())) {
            throw new CategoryExceptions.CategoryNameAlreadyExistsException("El nombre de la categoría ya existe");
        }

        return categoryPersistencePort.update(category);
    }

    @Override
    public List<Category> findAll(String order) {
        return categoryPersistencePort.findAll(order);
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
}