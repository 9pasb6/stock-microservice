package emazon.microservice.stock_microservice.controller;

import emazon.microservice.stock_microservice.aplication.dto.request.CategoryRequest;
import emazon.microservice.stock_microservice.aplication.dto.response.CategoryResponse;
import emazon.microservice.stock_microservice.aplication.handler.ICategoryHandler;
import emazon.microservice.stock_microservice.domain.exceptions.CategoryExceptions;
import emazon.microservice.stock_microservice.infraestructure.input.rest.controller.CategoryController;
import emazon.microservice.stock_microservice.infraestructure.input.rest.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CategoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ICategoryHandler categoryHandler;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testSaveCategory_Success() throws Exception {
        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Category\"}"))
                .andExpect(status().isCreated());

        verify(categoryHandler, times(1)).save(any(CategoryRequest.class));
    }

    @Test
    void testSaveCategory_InvalidInput() throws Exception {
        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());

        verify(categoryHandler, never()).save(any(CategoryRequest.class));
    }

    @Test
    void testGetAllCategories_Success() throws Exception {
        CategoryResponse category1 = new CategoryResponse();
        CategoryResponse category2 = new CategoryResponse();
        List<CategoryResponse> categories = Arrays.asList(category1, category2);

        when(categoryHandler.findAll("asc")).thenReturn(categories);

        mockMvc.perform(get("/api/categories")
                        .param("order", "asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));

        verify(categoryHandler, times(1)).findAll("asc");
    }

    @Test
    void testGetCategoryByName_Success() throws Exception {
        CategoryResponse category = new CategoryResponse();
        category.setId(1L);
        category.setName("series");
        category.setDescription("Category description");

        when(categoryHandler.findByName("series")).thenReturn(category);

        mockMvc.perform(get("/api/categories/series"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("series"))
                .andExpect(jsonPath("$.description").value("Category description"));

        verify(categoryHandler, times(1)).findByName("series");
    }

    @Test
    void testGetCategoryByName_NotFound() throws Exception {
        when(categoryHandler.findByName("Nonexistent Category"))
                .thenThrow(new CategoryExceptions.CategoryNotFoundException("The category with name 'Nonexistent Category' was not found."));

        MvcResult result = mockMvc.perform(get("/api/categories/Nonexistent Category"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("The category with name 'Nonexistent Category' was not found."))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        System.out.println("Response Body: " + responseBody);

        verify(categoryHandler, times(1)).findByName("Nonexistent Category");
    }

    @Test
    void testGetCategoriesByIds_Success() throws Exception {
        CategoryResponse category1 = new CategoryResponse();
        CategoryResponse category2 = new CategoryResponse();
        List<CategoryResponse> categories = Arrays.asList(category1, category2);

        when(categoryHandler.getCategoriesByIds(any(Set.class))).thenReturn(categories);

        mockMvc.perform(get("/api/categories/ids")
                        .param("ids", "1,2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));

        verify(categoryHandler, times(1)).getCategoriesByIds(any(Set.class));
    }

    @Test
    void testGetCategoriesByIds_NotFound() throws Exception {
        when(categoryHandler.getCategoriesByIds(any(Set.class))).thenReturn(Collections.emptyList());

        MvcResult result = mockMvc.perform(get("/api/categories/ids")
                        .param("ids", "1,2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(0))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        System.out.println("Response Body: " + responseBody);

        verify(categoryHandler, times(1)).getCategoriesByIds(any(Set.class));
    }

    @Test
    void testGetCategoryNamesByIds_Success() throws Exception {
        List<String> categoryNames = Arrays.asList("candy", "series", "games");

        when(categoryHandler.getCategoryNamesByIds(any(Set.class))).thenReturn(new HashSet<>(categoryNames));

        mockMvc.perform(get("/api/categories/names")
                        .param("ids", "1", "2", "3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0]").value("candy"))
                .andExpect(jsonPath("$[1]").value("series"))
                .andExpect(jsonPath("$[2]").value("games"));

        verify(categoryHandler, times(1)).getCategoryNamesByIds(any(Set.class));
    }

    @Test
    void testGetCategoryNamesByIds_PartialFailure() throws Exception {
        when(categoryHandler.getCategoryNamesByIds(any(Set.class)))
                .thenThrow(new CategoryExceptions.CategoryNotFoundException("Some category names not found for IDs: [1, 2, 34]"));

        mockMvc.perform(get("/api/categories/names")
                        .param("ids", "1", "2", "34"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("Some category names not found for IDs: [1, 2, 34]"));

        verify(categoryHandler, times(1)).getCategoryNamesByIds(any(Set.class));
    }

    @Test
    void testUpdateCategory_InvalidInput() throws Exception {
        mockMvc.perform(put("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());

        verify(categoryHandler, never()).update(any(CategoryRequest.class));
    }

    @Test
    void testDeleteCategoryById_Success() throws Exception {
        when(categoryHandler.deleteById(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/categories/1"))
                .andExpect(status().isOk());

        verify(categoryHandler, times(1)).deleteById(anyLong());
    }

    @Test
    void testDeleteCategoryById_NotFound() throws Exception {
        when(categoryHandler.deleteById(anyLong())).thenReturn(false);

        mockMvc.perform(delete("/api/categories/1"))
                .andExpect(status().isNotFound());

        verify(categoryHandler, times(1)).deleteById(anyLong());
    }

    @Test
    void testDeleteAllCategories_Success() throws Exception {
        mockMvc.perform(delete("/api/categories"))
                .andExpect(status().isOk());

        verify(categoryHandler, times(1)).deleteAll();
    }

}