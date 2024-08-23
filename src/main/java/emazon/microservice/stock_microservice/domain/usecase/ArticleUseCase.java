package emazon.microservice.stock_microservice.domain.usecase;

import emazon.microservice.stock_microservice.domain.api.IArticleServicePort;
import emazon.microservice.stock_microservice.domain.model.Article;
import emazon.microservice.stock_microservice.domain.model.Brand;
import emazon.microservice.stock_microservice.domain.model.Category;
import emazon.microservice.stock_microservice.domain.spi.IArticlePersistencePort;
import emazon.microservice.stock_microservice.domain.spi.IBrandPersistencePort;
import emazon.microservice.stock_microservice.domain.spi.ICategoryPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ArticleUseCase implements IArticleServicePort {

    private final IArticlePersistencePort articlePersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IBrandPersistencePort brandPersistencePort;

    public ArticleUseCase(IArticlePersistencePort articlePersistencePort, ICategoryPersistencePort categoryPersistencePort, IBrandPersistencePort brandPersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void saveArticle(Article article) {
        validateArticle(article); // Validar el artículo antes de guardar

        // Verificar si ya existe un artículo con el mismo nombre
        if (articlePersistencePort.existsByName(article.getName())) {
            throw new IllegalArgumentException("Article name already exists");
        }

        this.articlePersistencePort.saveArticle(article);
    }

    @Override
    public Page<Article> getAllArticles(Pageable pageable) {
        Page<Article> articles = this.articlePersistencePort.getAllArticles(pageable);

        // Modificar las categorías para devolver solo id y nombre
        articles.getContent().forEach(article -> {
            if (article.getCategories() != null) {
                article.setCategories(article.getCategories().stream()
                        .map(cat -> new Category(cat.getId(), cat.getName(), cat.getDescription())) // Suponiendo que Category tiene un constructor así
                        .collect(Collectors.toSet()));
            }
        });

        return articles;
    }


    @Override
    public Article getArticle(Long articleId) {
        return this.articlePersistencePort.getArticle(articleId);
    }

    @Override
    public void updateArticle(Article article) {
        validateArticle(article); // Validar el artículo antes de actualizar

        // Verificar si el nombre del artículo se está cambiando y si ya existe otro artículo con el mismo nombre
        Article existingArticle = articlePersistencePort.getArticle(article.getId());
        if (existingArticle != null && !Objects.equals(existingArticle.getName(), article.getName()) &&
                articlePersistencePort.existsByName(article.getName())) {
            throw new IllegalArgumentException("Article name already exists");
        }

        this.articlePersistencePort.updateArticle(article);
    }

    @Override
    public void deleteArticle(Long articleId) {
        this.articlePersistencePort.deleteArticle(articleId);
    }



    // Método para validar el artículo
    private void validateArticle(Article article) {
        if (article.getName() == null || article.getName().isEmpty()) {
            throw new IllegalArgumentException("Article name cannot be null or empty");
        }
        if (article.getDescription() == null || article.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Article description cannot be null or empty");
        }
        if (article.getPrice() == null || article.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Article price must be greater than zero");
        }
        if (article.getStockQuantity() == null || article.getStockQuantity() < 0) {
            throw new IllegalArgumentException("Article stock quantity cannot be negative");
        }
        if (article.getCategories() == null || article.getCategories().isEmpty() || article.getCategories().size() > 3) {
            throw new IllegalArgumentException("Article must have between 1 and 3 categories");
        }
        Set<Category> uniqueCategories = new HashSet<>(article.getCategories());
        if (uniqueCategories.size() != article.getCategories().size()) {
            throw new IllegalArgumentException("Article categories cannot have duplicate entries");
        }
    }
}