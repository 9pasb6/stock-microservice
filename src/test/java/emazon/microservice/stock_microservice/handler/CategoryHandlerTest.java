package emazon.microservice.stock_microservice.handler;

import emazon.microservice.stock_microservice.aplication.dto.request.CategoryRequest;
import emazon.microservice.stock_microservice.aplication.dto.response.CategoryResponse;
import emazon.microservice.stock_microservice.aplication.handler.impl.CategoryHandler;
import emazon.microservice.stock_microservice.aplication.mapper.request.CategoryRequestMapper;
import emazon.microservice.stock_microservice.aplication.mapper.response.CategoryResponseMapper;
import emazon.microservice.stock_microservice.domain.api.ICategoryServicePort;
import emazon.microservice.stock_microservice.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.*;

class CategoryHandlerTest {

    @Mock
    private ICategoryServicePort categoryServicePort;

    @Mock
    private CategoryRequestMapper categoryRequestMapper;

    @Mock
    private CategoryResponseMapper categoryResponseMapper;

    @InjectMocks
    private CategoryHandler categoryHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        CategoryRequest categoryRequest = new CategoryRequest();
        Category category = new Category();

        when(categoryRequestMapper.requestToCategory(any(CategoryRequest.class))).thenReturn(category);
        when(categoryServicePort.save(any(Category.class))).thenReturn(category);

        Category result = categoryHandler.save(categoryRequest);

        verify(categoryRequestMapper, times(1)).requestToCategory(categoryRequest);
        verify(categoryServicePort, times(1)).save(category);
        assertEquals(category, result);
    }

    @Test
    void testFindByName() {
        Category category = new Category();
        CategoryResponse categoryResponse = new CategoryResponse();

        when(categoryServicePort.findByName(anyString())).thenReturn(category);
        when(categoryResponseMapper.categoryToResponse(any(Category.class))).thenReturn(categoryResponse);

        CategoryResponse result = categoryHandler.findByName("categoryName");

        verify(categoryServicePort, times(1)).findByName("categoryName");
        verify(categoryResponseMapper, times(1)).categoryToResponse(category);
        assertEquals(categoryResponse, result);
    }



    @Test
    void testUpdate() {
        CategoryRequest categoryRequest = new CategoryRequest();
        Category category = new Category();

        when(categoryRequestMapper.requestToCategory(any(CategoryRequest.class))).thenReturn(category);
        when(categoryServicePort.update(any(Category.class))).thenReturn(category);

        Category result = categoryHandler.update(categoryRequest);

        verify(categoryRequestMapper, times(1)).requestToCategory(categoryRequest);
        verify(categoryServicePort, times(1)).update(category);
        assertEquals(category, result);
    }

    @Test
    void testFindAll() {
        Category category = new Category();
        CategoryResponse categoryResponse = new CategoryResponse();

        when(categoryServicePort.findAll(anyString())).thenReturn(Arrays.asList(category));
        when(categoryResponseMapper.categoryToResponse(any(Category.class))).thenReturn(categoryResponse);

        List<CategoryResponse> result = categoryHandler.findAll("asc");

        verify(categoryServicePort, times(1)).findAll("asc");
        verify(categoryResponseMapper, times(1)).categoryToResponse(category);
        assertEquals(1, result.size());
        assertEquals(categoryResponse, result.get(0));
    }

    @Test
    void testDeleteAll() {
        categoryHandler.deleteAll();

        verify(categoryServicePort, times(1)).deleteAll();
    }

    @Test
    void testDeleteById() {
        categoryHandler.deleteById(1L);

        verify(categoryServicePort, times(1)).deleteById(1L);
    }

    @Test
    void testGetCategoriesByIds() {
        Category category = new Category();
        CategoryResponse categoryResponse = new CategoryResponse();

        when(categoryServicePort.getCategoriesByIds(anySet())).thenReturn(Arrays.asList(category));
        when(categoryResponseMapper.categoryToResponse(any(Category.class))).thenReturn(categoryResponse);

        List<CategoryResponse> result = categoryHandler.getCategoriesByIds(Set.of(1L, 2L));

        verify(categoryServicePort, times(1)).getCategoriesByIds(Set.of(1L, 2L));
        verify(categoryResponseMapper, times(1)).categoryToResponse(category);
        assertEquals(1, result.size());
        assertEquals(categoryResponse, result.get(0));
    }

    @Test
    void testGetCategoryNamesByIds() {
        Set<String> categoryNames = Set.of("Category1", "Category2");

        when(categoryServicePort.getCategoryNamesByIds(anySet())).thenReturn(categoryNames);

        Set<String> result = categoryHandler.getCategoryNamesByIds(Set.of(1L, 2L));

        verify(categoryServicePort, times(1)).getCategoryNamesByIds(Set.of(1L, 2L));
        assertEquals(categoryNames, result);
    }
}