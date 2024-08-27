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
            throw new ArticleExceptions.ArticleNameAlreadyExistsException("The article name already exists.");
        }

        this.articlePersistencePort.saveArticle(article);
    }

    @Override
    public List<Article> getAllArticles(String order) {
        return this.articlePersistencePort.getAllArticles(order);
    }

    @Override
    public Article getArticle(Long articleId) {
        Article article = this.articlePersistencePort.getArticle(articleId);
        if (article == null) {
            throw new ArticleExceptions.ArticleNotFoundException("Article not found with ID: " + articleId);
        }
        return article;
    }

    @Override
    public void updateArticle(Article article) {
        ValidationUtils.validateArticle(article);

        Article existingArticle = articlePersistencePort.getArticle(article.getId());

        if (existingArticle != null && !existingArticle.getName().equals(article.getName()) &&
                articlePersistencePort.existsByName(article.getName())) {
            throw new ArticleExceptions.ArticleNameAlreadyExistsException("The article name already exists.");
        }
        this.articlePersistencePort.updateArticle(article);
    }

    @Override
    public void deleteArticle(Long articleId) {
        Article article = this.articlePersistencePort.getArticle(articleId);
        if (article == null) {
            throw new ArticleExceptions.ArticleNotFoundException("Article not found with ID: " + articleId);
        }
        this.articlePersistencePort.deleteArticle(articleId);
    }

    @Override
    public List<Article> getAllByBrandName(String brandName, String order) {
        if (!brandPersistencePort.existsByName(brandName)) {
            throw new ArticleExceptions.BrandNotFoundException("The brand '" + brandName + "' does not exist.");
        }
        return this.articlePersistencePort.findAllByBrandName(brandName, order);
    }

    @Override
    public List<Article> getAllByCategoryName(String categoryName, String order) {
        if (!categoryPersistencePort.existsByName(categoryName)) {
            throw new ArticleExceptions.CategoryNotFoundException("The category '" + categoryName + "' does not exist.");
        }
        return this.articlePersistencePort.findAllByCategoryName(categoryName, order);
    }
}