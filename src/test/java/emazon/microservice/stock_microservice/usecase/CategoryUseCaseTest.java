package emazon.microservice.stock_microservice.usecase;


import emazon.microservice.stock_microservice.domain.exceptions.CategoryExceptions;
import emazon.microservice.stock_microservice.domain.model.Category;
import emazon.microservice.stock_microservice.domain.spi.ICategoryPersistencePort;
import emazon.microservice.stock_microservice.domain.usecase.CategoryUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Validaciones

    @Test
    void testSaveCategory_NameAlreadyExistsException() {
        Category category = new Category();
        category.setName("Existing Category");
        category.setDescription("Description");

        when(categoryPersistencePort.existsByName(category.getName())).thenReturn(true);

        CategoryExceptions.CategoryNameAlreadyExistsException exception = assertThrows(
                CategoryExceptions.CategoryNameAlreadyExistsException.class,
                () -> categoryUseCase.save(category)
        );
        assertEquals("El nombre de la categoría ya existe", exception.getMessage());
    }

    @Test
    void testSaveCategory_ThrowsExceptionWhenNameIsNull() {
        Category category = new Category();
        category.setDescription("Description");

        assertThrows(IllegalArgumentException.class, () -> {
            categoryUseCase.save(category);
        }, "El nombre de la categoría no puede estar vacío o ser nulo");
    }

    @Test
    void testSaveCategory_ThrowsExceptionWhenDescriptionIsNull() {
        Category category = new Category();
        category.setName("New Category");

        assertThrows(IllegalArgumentException.class, () -> {
            categoryUseCase.save(category);
        }, "La descripción de la categoría no puede estar vacía o ser nula");
    }

    @Test
    void testSaveCategory_ThrowsExceptionWhenNameIsTooLong() {
        Category category = new Category();
        category.setName("A".repeat(51)); // 51 characters
        category.setDescription("Description");

        assertThrows(IllegalArgumentException.class, () -> {
            categoryUseCase.save(category);
        }, "El nombre de la categoría debe tener como máximo 50 caracteres");
    }

    @Test
    void testSaveCategory_ThrowsExceptionWhenDescriptionIsTooLong() {
        Category category = new Category();
        category.setName("New Category");
        category.setDescription("A".repeat(91)); // 91 characters

        assertThrows(IllegalArgumentException.class, () -> {
            categoryUseCase.save(category);
        }, "La descripción de la categoría debe tener como máximo 90 caracteres");
    }

    @Test
    void testUpdateCategory_NameAlreadyExistsException() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Updated Category");
        category.setDescription("Updated Description");

        Category existingCategory = new Category();
        existingCategory.setName("Existing Category");

        when(categoryPersistencePort.findById(category.getId())).thenReturn(existingCategory);
        when(categoryPersistencePort.existsByName(category.getName())).thenReturn(true);

        CategoryExceptions.CategoryNameAlreadyExistsException exception = assertThrows(
                CategoryExceptions.CategoryNameAlreadyExistsException.class,
                () -> categoryUseCase.update(category)
        );
        assertEquals("El nombre de la categoría ya existe", exception.getMessage());
    }

    @Test
    void testUpdateCategory_Success() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Updated Category");
        category.setDescription("Updated Description");

        Category existingCategory = new Category();
        existingCategory.setName("Old Category");

        when(categoryPersistencePort.findById(category.getId())).thenReturn(existingCategory);
        when(categoryPersistencePort.existsByName(category.getName())).thenReturn(false);

        categoryUseCase.update(category);
        verify(categoryPersistencePort, times(1)).update(category);
    }

    // CRUD

    @Test
    void testFindAllCategories() {
        List<Category> categories = new ArrayList<>();
        when(categoryPersistencePort.findAll("asc")).thenReturn(categories);

        List<Category> result = categoryUseCase.findAll("asc");
        assertEquals(categories, result);
    }

    @Test
    void testFindCategoryByName() {
        Category category = new Category();
        category.setName("Category Name");

        when(categoryPersistencePort.findByName("Category Name")).thenReturn(category);

        Category result = categoryUseCase.findByName("Category Name");
        assertEquals(category, result);
    }

    @Test
    void testFindCategoryByName_CategoryNotFoundException() {
        when(categoryPersistencePort.findByName("NonExistentCategory")).thenReturn(null);

        CategoryExceptions.CategoryNotFoundException exception = assertThrows(
                CategoryExceptions.CategoryNotFoundException.class,
                () -> categoryUseCase.findByName("NonExistentCategory")
        );
        assertEquals("La categoría con nombre 'NonExistentCategory' no se encuentra", exception.getMessage());
    }

    @Test
    void testDeleteCategory() {
        Category category = new Category();
        category.setId(1L);

        categoryUseCase.delete(category);
        verify(categoryPersistencePort, times(1)).delete(category);
    }

    @Test
    void testDeleteAllCategories() {
        categoryUseCase.deleteAll();
        verify(categoryPersistencePort, times(1)).deleteAll();
    }

    @Test
    void testDeleteCategoryById() {
        categoryUseCase.deleteById(1L);
        verify(categoryPersistencePort, times(1)).deleteById(1L);
    }

    @Test
    void testGetCategoriesByIds() {
        Set<Long> ids = new HashSet<>();
        ids.add(1L);
        ids.add(2L);

        List<Category> categories = new ArrayList<>();
        when(categoryPersistencePort.getCategoriesByIds(ids)).thenReturn(categories);

        List<Category> result = categoryUseCase.getCategoriesByIds(ids);
        assertEquals(categories, result);
    }

    @Test
    void testGetCategoryNamesByIds() {
        Set<Long> ids = new HashSet<>();
        ids.add(1L);
        ids.add(2L);

        Set<String> categoryNames = new HashSet<>();
        categoryNames.add("Category1");
        categoryNames.add("Category2");

        when(categoryPersistencePort.getCategoryNamesByIds(ids)).thenReturn(categoryNames);

        Set<String> result = categoryUseCase.getCategoryNamesByIds(ids);
        assertEquals(categoryNames, result);
    }
}