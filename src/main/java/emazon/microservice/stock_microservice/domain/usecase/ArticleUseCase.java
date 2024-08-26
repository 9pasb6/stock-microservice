package emazon.microservice.stock_microservice.domain.usecase;

import emazon.microservice.stock_microservice.domain.api.IArticleServicePort;
import emazon.microservice.stock_microservice.domain.exceptions.ArticleExceptions;
import emazon.microservice.stock_microservice.domain.model.Article;
import emazon.microservice.stock_microservice.domain.spi.IArticlePersistencePort;
import emazon.microservice.stock_microservice.domain.spi.IBrandPersistencePort;
import emazon.microservice.stock_microservice.domain.spi.ICategoryPersistencePort;
import emazon.microservice.stock_microservice.domain.util.ValidationUtils;
import java.util.List;

public class ArticleUseCase implements IArticleServicePort {

    private final IArticlePersistencePort articlePersistencePort;
    private final IBrandPersistencePort brandPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;

    public ArticleUseCase(IArticlePersistencePort articlePersistencePort,
                          IBrandPersistencePort brandPersistencePort,
                          ICategoryPersistencePort categoryPersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
        this.brandPersistencePort = brandPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
    }

    public void saveArticle(Article article) {

        ValidationUtils.validateArticle(article);

        if (articlePersistencePort.existsByName(article.getName())) {
            throw new ArticleExceptions.ArticleNameAlreadyExistsException("El nombre del artículo ya existe");
        }

        this.articlePersistencePort.saveArticle(article);
    }
    @Override
    public List<Article> getAllArticles(String order) {
        return this.articlePersistencePort.getAllArticles(order);
    }

    @Override
    public Article getArticle(Long articleId) {
        return this.articlePersistencePort.getArticle(articleId);
    }

    @Override
    public void updateArticle(Article article) {
        ValidationUtils.validateArticle(article);

        Article existingArticle = articlePersistencePort.getArticle(article.getId());

        if (existingArticle != null && !existingArticle.getName().equals(article.getName()) &&
                articlePersistencePort.existsByName(article.getName())) {
            throw new ArticleExceptions.ArticleNameAlreadyExistsException("El nombre del artículo ya existe");
        }
        this.articlePersistencePort.updateArticle(article);
    }

    @Override
    public void deleteArticle(Long articleId) {
        this.articlePersistencePort.deleteArticle(articleId);
    }

    @Override
    public List<Article> getAllByBrandName(String brandName, String order) {
        if (!brandPersistencePort.existsByName(brandName)) {
            throw new ArticleExceptions.BrandNotFoundException("La marca '" + brandName + "' no existe.");
        }
        return this.articlePersistencePort.findAllByBrandName(brandName, order);
    }

    @Override
    public List<Article> getAllByCategoryName(String categoryName, String order) {
        if (!categoryPersistencePort.existsByName(categoryName)) {
            throw new ArticleExceptions.CategoryNotFoundException("La categoría '" + categoryName + "' no existe.");
        }
        return this.articlePersistencePort.findAllByCategoryName(categoryName, order);
    }
}