package emazon.microservice.stock_microservice.domain.api;

import emazon.microservice.stock_microservice.domain.model.Article;

import java.util.List;

public interface IArticleServicePort {

        void saveArticle(Article article);

        List<Article> getAllArticles(String order);

        Article getArticle(Long id);

        void updateArticle(Article article);

        void deleteArticle(Long id);

        List<Article> getAllByBrandName(String brandName, String order); // Nuevo método

        List<Article> getAllByCategoryName(String categoryName, String order); // Nuevo método
}