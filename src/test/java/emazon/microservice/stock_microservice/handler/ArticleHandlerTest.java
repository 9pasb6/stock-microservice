package emazon.microservice.stock_microservice.handler;

import emazon.microservice.stock_microservice.aplication.dto.request.ArticleRequest;
import emazon.microservice.stock_microservice.aplication.dto.response.ArticleResponse;
import emazon.microservice.stock_microservice.aplication.handler.impl.ArticleHandler;
import emazon.microservice.stock_microservice.aplication.mapper.request.ArticleRequestMapper;
import emazon.microservice.stock_microservice.aplication.mapper.response.ArticleResponseMapper;
import emazon.microservice.stock_microservice.domain.api.IArticleServicePort;
import emazon.microservice.stock_microservice.domain.model.Article;
import emazon.microservice.stock_microservice.domain.model.Brand;
import emazon.microservice.stock_microservice.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ArticleHandlerTest {

    @Mock
    private IArticleServicePort articleServicePort;

    @Mock
    private ArticleRequestMapper articleRequestMapper;

    @Mock
    private ArticleResponseMapper articleResponseMapper;

    @InjectMocks
    private ArticleHandler articleHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveArticle() {
        ArticleRequest articleRequest = new ArticleRequest("Test Article", "Test Description", BigDecimal.valueOf(123.213), 2, 1L, Arrays.asList(1L));
        Category category = new Category(1L, "Electronics", "Description");
        Brand brand = new Brand(1L, "Sonny", "Description");

        Article article = new Article();
        article.setName("Test Article");
        article.setDescription("Test Description");
        article.setPrice(BigDecimal.valueOf(123.213));
        article.setStockQuantity(2);
        article.setBrand(brand);
        article.setCategories(Arrays.asList(category));

        when(articleRequestMapper.requestToArticle(any(ArticleRequest.class))).thenReturn(article);

        articleHandler.saveArticle(articleRequest);

        verify(articleRequestMapper, times(1)).requestToArticle(articleRequest);
        verify(articleServicePort, times(1)).saveArticle(article);
    }

    @Test
    void testGetAllArticles() {
        Article article = new Article();
        ArticleResponse articleResponse = new ArticleResponse();

        when(articleServicePort.getAllArticles(anyString())).thenReturn(Arrays.asList(article));
        when(articleResponseMapper.articleToArticleResponse(any(Article.class))).thenReturn(articleResponse);

        List<ArticleResponse> result = articleHandler.getAllArticles("asc");

        verify(articleServicePort, times(1)).getAllArticles("asc");
        verify(articleResponseMapper, times(1)).articleToArticleResponse(article);
        assertEquals(1, result.size());
        assertEquals(articleResponse, result.get(0));
    }

    @Test
    void testGetArticle() {
        Article article = new Article();
        ArticleResponse articleResponse = new ArticleResponse();

        when(articleServicePort.getArticle(anyLong())).thenReturn(article);
        when(articleResponseMapper.articleToArticleResponse(any(Article.class))).thenReturn(articleResponse);

        ArticleResponse result = articleHandler.getArticle(1L);

        verify(articleServicePort, times(1)).getArticle(1L);
        verify(articleResponseMapper, times(1)).articleToArticleResponse(article);
        assertEquals(articleResponse, result);
    }

    @Test
    void testUpdateArticle() {
        ArticleRequest articleRequest = new ArticleRequest("Updated Article", "Updated Description", BigDecimal.valueOf(456.789), 5, 1L, Arrays.asList(1L));
        Article article = new Article();

        when(articleRequestMapper.requestToArticle(any(ArticleRequest.class))).thenReturn(article);

        articleHandler.updateArticle(articleRequest);

        verify(articleRequestMapper, times(1)).requestToArticle(articleRequest);
        verify(articleServicePort, times(1)).updateArticle(article);
    }

    @Test
    void testDeleteArticle() {
        articleHandler.deleteArticle(1L);

        verify(articleServicePort, times(1)).deleteArticle(1L);
    }

    @Test
    void testGetAllByBrandName() {
        Article article = new Article();
        ArticleResponse articleResponse = new ArticleResponse();

        when(articleServicePort.getAllByBrandName(anyString(), anyString())).thenReturn(Arrays.asList(article));
        when(articleResponseMapper.articleToArticleResponse(any(Article.class))).thenReturn(articleResponse);

        List<ArticleResponse> result = articleHandler.getAllByBrandName("brand", "asc");

        verify(articleServicePort, times(1)).getAllByBrandName("brand", "asc");
        verify(articleResponseMapper, times(1)).articleToArticleResponse(article);
        assertEquals(1, result.size());
        assertEquals(articleResponse, result.get(0));
    }

    @Test
    void testGetAllByCategoryName() {
        Article article = new Article();
        ArticleResponse articleResponse = new ArticleResponse();

        when(articleServicePort.getAllByCategoryName(anyString(), anyString())).thenReturn(Arrays.asList(article));
        when(articleResponseMapper.articleToArticleResponse(any(Article.class))).thenReturn(articleResponse);

        List<ArticleResponse> result = articleHandler.getAllByCategoryName("category", "asc");

        verify(articleServicePort, times(1)).getAllByCategoryName("category", "asc");
        verify(articleResponseMapper, times(1)).articleToArticleResponse(article);
        assertEquals(1, result.size());
        assertEquals(articleResponse, result.get(0));
    }
}