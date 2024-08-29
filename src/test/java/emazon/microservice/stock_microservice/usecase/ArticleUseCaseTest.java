package emazon.microservice.stock_microservice.usecase;

import emazon.microservice.stock_microservice.domain.api.IArticleServicePort;
import emazon.microservice.stock_microservice.domain.exceptions.ArticleExceptions;
import emazon.microservice.stock_microservice.domain.model.Article;
import emazon.microservice.stock_microservice.domain.model.Brand;
import emazon.microservice.stock_microservice.domain.model.Category;
import emazon.microservice.stock_microservice.domain.spi.IArticlePersistencePort;
import emazon.microservice.stock_microservice.domain.spi.IBrandPersistencePort;
import emazon.microservice.stock_microservice.domain.spi.ICategoryPersistencePort;
import emazon.microservice.stock_microservice.domain.usecase.ArticleUseCase;
import emazon.microservice.stock_microservice.domain.util.ValidationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArticleUseCaseTest {

    @Mock
    private IArticlePersistencePort articlePersistencePort;

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private ArticleUseCase articleUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testSaveArticle_ArticleNameAlreadyExistsException() {
        Category category = new Category(1L, "Electronics", "Description");
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        Brand brand = new Brand(1L, "Sonny", "Description");

        Article article = new Article();
        article.setName("Test Article");
        article.setDescription("Test Description");
        article.setPrice(BigDecimal.valueOf(123.213));
        article.setStockQuantity(2);
        article.setBrand(brand);
        article.setCategories(categories);

        when(articlePersistencePort.existsByName(article.getName())).thenReturn(true);

        ArticleExceptions.ArticleNameAlreadyExistsException exception = assertThrows(
                ArticleExceptions.ArticleNameAlreadyExistsException.class,
                () -> articleUseCase.saveArticle(article)
        );
        assertEquals("The article name already exists.", exception.getMessage());
    }

    @Test
    void testSaveArticle_ThrowsExceptionWhenDescriptionIsNull() {
        Article article = new Article();
        article.setName("New Article");

        when(articlePersistencePort.existsByName(article.getName())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            articleUseCase.saveArticle(article);
        }, "La descripción del artículo no puede estar vacía o ser nula");
    }

    @Test
    void testValidateArticle_InsufficientCategories() {
        Brand brand = new Brand(1L, "Sonny", "Description");

        Article article = new Article();
        article.setName("Test Article");
        article.setDescription("Test Description");
        article.setPrice(BigDecimal.valueOf(123.45));
        article.setStockQuantity(10);
        article.setBrand(brand);
        article.setCategories(new ArrayList<>());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> ValidationUtils.validateArticle(article)
        );

        assertEquals("The article must have between 1 and 3 categories.", exception.getMessage());
    }

    @Test
    void testValidateArticle_TooManyCategories() {
        Category category1 = new Category(1L, "Electronics", "Description");
        Category category2 = new Category(2L, "Appliances", "Description");
        Category category3 = new Category(3L, "Furniture", "Description");
        Category category4 = new Category(4L, "Toys", "Description"); // Exceso

        List<Category> categories = Arrays.asList(category1, category2, category3, category4);
        Brand brand = new Brand(1L, "Sonny", "Description");

        Article article = new Article();
        article.setName("Test Article");
        article.setDescription("Test Description");
        article.setPrice(BigDecimal.valueOf(123.45));
        article.setStockQuantity(10);
        article.setBrand(brand);
        article.setCategories(categories);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> ValidationUtils.validateArticle(article)
        );

        assertEquals("The article must have between 1 and 3 categories.", exception.getMessage());
    }

    @Test
    void testSaveArticle_WithDuplicateCategories_ThrowsException() {

        Category category1 = new Category(1L, "Electronics", "Description");
        Category category2 = new Category(1L, "Electronics", "Description");

        List<Category> categories = new ArrayList<>(Arrays.asList(category1, category2));

        Article article = new Article();
        article.setName("Test Article");
        article.setDescription("Test Description");
        article.setPrice(BigDecimal.valueOf(123.45));
        article.setStockQuantity(10);
        article.setBrand(new Brand(1L, "Brand", "Description"));
        article.setCategories(categories);


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> articleUseCase.saveArticle(article)
        );

        assertEquals("The article cannot have duplicate categories.", exception.getMessage());
    }

    @Test
    void testGetAllByBrandName_BrandNotFoundException() {
        when(brandPersistencePort.existsByName("NonExistentBrand")).thenReturn(false);

        ArticleExceptions.BrandNotFoundException exception = assertThrows(
                ArticleExceptions.BrandNotFoundException.class,
                () -> articleUseCase.getAllByBrandName("NonExistentBrand", "asc")
        );
        assertEquals("The brand 'NonExistentBrand' does not exist.", exception.getMessage());
    }

    @Test
    void testGetAllByCategoryName_CategoryNotFoundException() {
        when(categoryPersistencePort.existsByName("NonExistentCategory")).thenReturn(false);

        ArticleExceptions.CategoryNotFoundException exception = assertThrows(
                ArticleExceptions.CategoryNotFoundException.class,
                () -> articleUseCase.getAllByCategoryName("NonExistentCategory", "asc")
        );
        assertEquals("The category 'NonExistentCategory' does not exist.", exception.getMessage());
    }

    // CRUD

    @Test
    void testGetAllArticles() {
        List<Article> articles = new ArrayList<>();
        when(articlePersistencePort.getAllArticles("asc")).thenReturn(articles);

        List<Article> result = articleUseCase.getAllArticles("asc");
        assertEquals(articles, result);
    }

    @Test
    void testGetArticle() {
        Article article = new Article();
        article.setId(1L);

        when(articlePersistencePort.getArticle(1L)).thenReturn(article);

        Article result = articleUseCase.getArticle(1L);
        assertEquals(article, result);
    }

    @Test
    void testUpdateArticle_ArticleNameAlreadyExistsException() {
        Category category = new Category(1L, "Electronics", "Description");
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        Brand brand = new Brand(1L, "Sonny", "Description");

        Article article = new Article();
        article.setId(1L);
        article.setName("Updated Article");
        article.setDescription("Updated Description");
        article.setPrice(BigDecimal.valueOf(123.213));
        article.setStockQuantity(2);
        article.setBrand(brand);
        article.setCategories(categories);

        Article existingArticle = new Article();
        existingArticle.setName("Article name");

        when(articlePersistencePort.getArticle(article.getId())).thenReturn(existingArticle);
        when(articlePersistencePort.existsByName(article.getName())).thenReturn(true);

        ArticleExceptions.ArticleNameAlreadyExistsException exception = assertThrows(
                ArticleExceptions.ArticleNameAlreadyExistsException.class,
                () -> articleUseCase.updateArticle(article)
        );
        assertEquals("The article name already exists.", exception.getMessage());
    }

    @Test
    void testUpdateArticle_Success() {
        Category category = new Category(1L, "Electronics", "Description");
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        Brand brand = new Brand(1L, "Sonny", "Description");

        Article article = new Article();
        article.setId(1L);
        article.setName("Updated Article");
        article.setDescription("Updated Description");
        article.setPrice(BigDecimal.valueOf(123.213));
        article.setStockQuantity(2);
        article.setBrand(brand);
        article.setCategories(categories);

        Article existingArticle = new Article();
        existingArticle.setName("Old Name");

        when(articlePersistencePort.getArticle(article.getId())).thenReturn(existingArticle);
        when(articlePersistencePort.existsByName(article.getName())).thenReturn(false);

        articleUseCase.updateArticle(article);
        verify(articlePersistencePort, times(1)).updateArticle(article);
    }

    @Test
    void testDeleteArticleById() {
        when(articlePersistencePort.existsById(1L)).thenReturn(true);
        articleUseCase.deleteArticle(1L);
        verify(articlePersistencePort, times(1)).deleteArticle(1L);
    }


    @Test
    void testGetAllByBrandName_Success() {
        List<Article> articles = new ArrayList<>();
        when(brandPersistencePort.existsByName("ValidBrand")).thenReturn(true);
        when(articlePersistencePort.findAllByBrandName("ValidBrand", "asc")).thenReturn(articles);

        List<Article> result = articleUseCase.getAllByBrandName("ValidBrand", "asc");

                assertEquals(articles, result);
    }
}