package emazon.microservice.stock_microservice.domain.usecase;

import emazon.microservice.stock_microservice.domain.api.IArticleServicePort;
import emazon.microservice.stock_microservice.domain.exceptions.ArticleExceptions;
import emazon.microservice.stock_microservice.domain.exceptions.BrandExceptions;
import emazon.microservice.stock_microservice.domain.exceptions.CategoryExceptions;
import emazon.microservice.stock_microservice.domain.model.Article;
import emazon.microservice.stock_microservice.domain.model.Brand;
import emazon.microservice.stock_microservice.domain.model.Category;
import emazon.microservice.stock_microservice.domain.spi.IArticlePersistencePort;
import emazon.microservice.stock_microservice.domain.spi.IBrandPersistencePort;
import emazon.microservice.stock_microservice.domain.spi.ICategoryPersistencePort;
import emazon.microservice.stock_microservice.domain.util.ErrorMessages;
import emazon.microservice.stock_microservice.domain.util.ValidationUtils;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public void saveArticle(Article article) {
        ValidationUtils.validateArticle(article);

        if (articlePersistencePort.existsByName(article.getName())) {
            throw new ArticleExceptions.ArticleNameAlreadyExistsException(ErrorMessages.ARTICLE_NAME_ALREADY_EXISTS);
        }
        Brand brand = brandPersistencePort.findById(article.getBrandId());
        if (brand == null) {
            throw new BrandExceptions.BrandNotFoundException(ErrorMessages.BRAND_NOT_FOUND + brand + ErrorMessages.BRAND_NOT_FOUND_SUFFIX);
        }
        List<Category> categories = article.getCategories();
        Set<Long> categoryIds = categories.stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
        Set<String> categoryNames = categoryPersistencePort.getCategoryNamesByIds(categoryIds);
        if (categoryNames.isEmpty()) {
            throw new CategoryExceptions.CategoryNotFoundException(ErrorMessages.CATEGORY_NAMES_NOT_FOUND_FOR_IDS + categoryIds);
        }
        if (categoryNames.size() != categoryIds.size()) {
            throw new CategoryExceptions.CategoryNotFoundException(ErrorMessages.SOME_CATEGORY_NAMES_NOT_FOUND_FOR_IDS + categoryIds);
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
            throw new ArticleExceptions.ArticleNotFoundException(ErrorMessages.ARTICLE_NOT_FOUND + articleId);
        }
        return article;
    }

    @Override
    public void updateArticle(Article article) {
        ValidationUtils.validateArticle(article);

        Article existingArticle = articlePersistencePort.getArticle(article.getId());

        if (existingArticle != null && !existingArticle.getName().equals(article.getName()) &&
                articlePersistencePort.existsByName(article.getName())) {
            throw new ArticleExceptions.ArticleNameAlreadyExistsException(ErrorMessages.ARTICLE_NAME_ALREADY_EXISTS);
        }
        this.articlePersistencePort.updateArticle(article);
    }

    @Override
    public void deleteArticle(Long articleId) {
        if (!articlePersistencePort.existsById(articleId)) {
            throw new ArticleExceptions.ArticleNotFoundException(ErrorMessages.ARTICLE_NOT_FOUND + articleId);
        }
        this.articlePersistencePort.deleteArticle(articleId);
    }

    @Override
    public List<Article> getAllByBrandName(String brandName, String order) {
        if (!brandPersistencePort.existsByName(brandName)) {
            throw new ArticleExceptions.BrandNotFoundException(ErrorMessages.BRAND_NOT_FOUND_FOR_ARTICLE + brandName + ErrorMessages.DOES_NOT_EXIST);
        }
        return this.articlePersistencePort.findAllByBrandName(brandName, order);
    }

    @Override
    public List<Article> getAllByCategoryName(String categoryName, String order) {
        if (!categoryPersistencePort.existsByName(categoryName)) {
            throw new ArticleExceptions.CategoryNotFoundException(ErrorMessages.CATEGORY_NOT_FOUND_FOR_ARTICLE + categoryName + ErrorMessages.DOES_NOT_EXIST);
        }
        return this.articlePersistencePort.findAllByCategoryName(categoryName, order);
    }
}