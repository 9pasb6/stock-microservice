package emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.adapter;

import emazon.microservice.stock_microservice.domain.model.Category;
import emazon.microservice.stock_microservice.domain.spi.ICategoryPersistencePort;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.entity.CategoryEntity;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.mapper.ICategoryEntityMapper;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final CategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Override
    public Category save(Category category) {
        System.out.println("Saving brand: " + category.getName());
        categoryRepository.save(categoryEntityMapper.categoryToCategoryEntity(category));
        return  categoryEntityMapper.categoryEntityToCategory(categoryRepository.save(categoryEntityMapper.categoryToCategoryEntity(category)));



    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findAll().stream()
                .filter(categoryEntity -> categoryEntity.getName().equals(name))
                .map(categoryEntityMapper::categoryEntityToCategory)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(Category category) {
        categoryRepository.delete(categoryEntityMapper.categoryToCategoryEntity(category));
    }

    @Override
    public Category update(Category category) {


            return  categoryEntityMapper.categoryEntityToCategory(categoryRepository.save(categoryEntityMapper.categoryToCategoryEntity(category)));


    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryEntityMapper::categoryEntityToCategory)
                .toList();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryEntityMapper::categoryEntityToCategory)
                .orElse(null);
    }

    @Override
    public void deleteAll() {
        categoryRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> getCategoriesByIds(Set<Long> ids) {
        return categoryRepository.findAllById(ids)
                .stream()
                .map(categoryEntityMapper::categoryEntityToCategory)
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> getCategoryNamesByIds(Set<Long> ids) {
        return categoryRepository.findAllById(ids)
                .stream()
                .map(CategoryEntity::getName) // Aquí CategoryEntity es la clase y getName es el método de instancia
                .collect(Collectors.toSet());
    }
}