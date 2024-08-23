package emazon.microservice.stock_microservice.domain.usecase;

import emazon.microservice.stock_microservice.domain.api.ICategoryServicePort;
import emazon.microservice.stock_microservice.domain.model.Category;
import emazon.microservice.stock_microservice.domain.spi.ICategoryPersistencePort;

import java.util.List;
import java.util.Set;

public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }


    @Override
    public Category save(Category category) {

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

        return categoryPersistencePort.update(category);
    }

    @Override
    public List<Category> findAll() {

        return categoryPersistencePort.findAll();
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
        return List.of();
    }

    @Override
    public Set<String> getCategoryNamesByIds(Set<Long> ids) {
        return Set.of();
    }


}
