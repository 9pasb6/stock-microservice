package emazon.microservice.stock_microservice.controller;

import emazon.microservice.stock_microservice.aplication.dto.request.ArticleRequest;
import emazon.microservice.stock_microservice.aplication.dto.response.ArticleResponse;
import emazon.microservice.stock_microservice.aplication.handler.IArticleHandler;
import emazon.microservice.stock_microservice.infraestructure.input.rest.controller.ArticleController;
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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ArticleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IArticleHandler articleHandler;

    @InjectMocks
    private ArticleController articleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(articleController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testSaveArticle_Success() throws Exception {
        ArticleRequest articleRequest = new ArticleRequest();
        articleRequest.setName("Test Article");
        articleRequest.setDescription("Test Description");
        articleRequest.setPrice(BigDecimal.valueOf(123.45));
        articleRequest.setStockQuantity(10);

        mockMvc.perform(post("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Article\", \"description\":\"Test Description\", \"price\":123.45, \"stockQuantity\":10}"))
                .andExpect(status().isCreated());

        verify(articleHandler, times(1)).saveArticle(any(ArticleRequest.class));
    }

    @Test
    void testSaveArticle_InvalidInput() throws Exception {
        mockMvc.perform(post("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());

        verify(articleHandler, never()).saveArticle(any(ArticleRequest.class));
    }

    @Test
    void testGetAllArticles_Success() throws Exception {
        ArticleResponse article1 = new ArticleResponse();
        ArticleResponse article2 = new ArticleResponse();
        List<ArticleResponse> articles = Arrays.asList(article1, article2);

        when(articleHandler.getAllArticles("asc")).thenReturn(articles);

        mockMvc.perform(get("/api/articles")
                        .param("order", "asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));

        verify(articleHandler, times(1)).getAllArticles("asc");
    }

    @Test
    void testGetArticle_Success() throws Exception {
        ArticleResponse article = new ArticleResponse();

        when(articleHandler.getArticle(anyLong())).thenReturn(article);

        mockMvc.perform(get("/api/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(articleHandler, times(1)).getArticle(anyLong());
    }

    @Test
    void testGetArticle_NotFound() throws Exception {

        when(articleHandler.getArticle(1L)).thenReturn(null);


        MvcResult result = mockMvc.perform(get("/api/articles/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("Article not found with ID: 1"))
                .andReturn();


        String responseBody = result.getResponse().getContentAsString();
        System.out.println("Response Body: " + responseBody);


        verify(articleHandler, times(1)).getArticle(1L);
    }

    @Test
    void testUpdateArticle_Success() throws Exception {
        ArticleRequest articleRequest = new ArticleRequest();
        articleRequest.setName("Updated Article");
        articleRequest.setDescription("Updated Description");
        articleRequest.setPrice(BigDecimal.valueOf(456.78));
        articleRequest.setStockQuantity(20);

        mockMvc.perform(put("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Article\", \"description\":\"Updated Description\", \"price\":456.78, \"stockQuantity\":20}"))
                .andExpect(status().isOk());

        verify(articleHandler, times(1)).updateArticle(any(ArticleRequest.class));
    }

    @Test
    void testUpdateArticle_InvalidInput() throws Exception {
        mockMvc.perform(put("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());

        verify(articleHandler, never()).updateArticle(any(ArticleRequest.class));
    }

    @Test
    void testDeleteArticle_Success() throws Exception {
        when(articleHandler.deleteArticle(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/articles/1"))
                .andExpect(status().isOk());

        verify(articleHandler, times(1)).deleteArticle(anyLong());
    }

    @Test
    void testDeleteArticle_NotFound() throws Exception {
        when(articleHandler.deleteArticle(anyLong())).thenReturn(false);

        mockMvc.perform(delete("/api/articles/1"))
                .andExpect(status().isNotFound());

        verify(articleHandler, times(1)).deleteArticle(anyLong());
    }


    @Test
    void testGetAllByBrandName_Success() throws Exception {
        ArticleResponse article1 = new ArticleResponse();
        ArticleResponse article2 = new ArticleResponse();
        List<ArticleResponse> articles = Arrays.asList(article1, article2);

        when(articleHandler.getAllByBrandName("Sonny", "asc")).thenReturn(articles);

        mockMvc.perform(get("/api/articles/brand")
                        .param("brandName", "Sonny")
                        .param("order", "asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));

        verify(articleHandler, times(1)).getAllByBrandName("Sonny", "asc");
    }

    @Test
    void testGetAllByCategoryName_Success() throws Exception {
        ArticleResponse article1 = new ArticleResponse();
        ArticleResponse article2 = new ArticleResponse();
        List<ArticleResponse> articles = Arrays.asList(article1, article2);

        when(articleHandler.getAllByCategoryName("Electronics", "asc")).thenReturn(articles);

        mockMvc.perform(get("/api/articles/category")
                        .param("categoryName", "Electronics")
                        .param("order", "asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));

        verify(articleHandler, times(1)).getAllByCategoryName("Electronics", "asc");
    }
}