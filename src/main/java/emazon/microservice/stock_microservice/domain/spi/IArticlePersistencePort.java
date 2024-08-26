package emazon.microservice.stock_microservice.domain.spi;

import emazon.microservice.stock_microservice.domain.model.Article;

import java.util.List;

public interface IArticlePersistencePort {

    void saveArticle(Article article);

    List<Article> getAllArticles(String order);

    Article getArticle(Long articleId);

    void updateArticle(Article article);

    void deleteArticle(Long articleId);

    boolean existsByName(String name);

    List<Article> findAllByBrandName(String brandName, String order);

    List<Article> findAllByCategoryName(String categoryName, String order);

}