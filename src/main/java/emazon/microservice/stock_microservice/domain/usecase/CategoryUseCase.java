package emazon.microservice.stock_microservice.domain.usecase;

import emazon.microservice.stock_microservice.domain.api.ICategoryServicePort;
import emazon.microservice.stock_microservice.domain.exceptions.CategoryExceptions;
import emazon.microservice.stock_microservice.domain.model.Category;
import emazon.microservice.stock_microservice.domain.spi.ICategoryPersistencePort;
import emazon.microservice.stock_microservice.domain.util.ErrorMessages;
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
            throw new CategoryExceptions.CategoryNameAlreadyExistsException(ErrorMessages.CATEGORY_NAME_ALREADY_EXISTS);
        }

        return categoryPersistencePort.save(category);
    }

    @Override
    public Category findByName(String name) {
        Category category = categoryPersistencePort.findByName(name);
        if (category == null) {
            throw new CategoryExceptions.CategoryNotFoundException(ErrorMessages.CATEGORY_NAME_NOT_FOUND + name + ErrorMessages.CATEGORY_NOT_FOUND_SUFFIX);
        }
        return category;
    }

    @Override
    public Category update(Category category) {
        ValidationUtils.validateCategory(category);

        Category existingCategory = categoryPersistencePort.findById(category.getId());
        if (existingCategory != null && !Objects.equals(existingCategory.getName(), category.getName()) &&
                categoryPersistencePort.existsByName(category.getName())) {
            throw new CategoryExceptions.CategoryNameAlreadyExistsException(ErrorMessages.CATEGORY_NAME_ALREADY_EXISTS);
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
        if (!categoryPersistencePort.existsById(id)) {
            throw new CategoryExceptions.CategoryNotFoundException(ErrorMessages.CATEGORY_NOT_FOUND + id);
        }
        categoryPersistencePort.deleteById(id);
    }

    @Override
    public List<Category> getCategoriesByIds(Set<Long> ids) {
        List<Category> categories = categoryPersistencePort.getCategoriesByIds(ids);
        if (categories.isEmpty()) {
            throw new CategoryExceptions.CategoryNotFoundException(ErrorMessages.CATEGORIES_NOT_FOUND_FOR_IDS + ids);
        }
        return categories;
    }

    @Override
    public Set<String> getCategoryNamesByIds(Set<Long> ids) {
        Set<String> categoryNames = categoryPersistencePort.getCategoryNamesByIds(ids);
        if (categoryNames.isEmpty()) {
            throw new CategoryExceptions.CategoryNotFoundException(ErrorMessages.CATEGORY_NAMES_NOT_FOUND_FOR_IDS + ids);
        }
        if (categoryNames.size() != ids.size()) {
            throw new CategoryExceptions.CategoryNotFoundException(ErrorMessages.SOME_CATEGORY_NAMES_NOT_FOUND_FOR_IDS + ids);
        }
        return categoryNames;
    }
}