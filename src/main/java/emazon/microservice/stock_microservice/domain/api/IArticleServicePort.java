package emazon.microservice.stock_microservice.domain.api;

import emazon.microservice.stock_microservice.domain.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IArticleServicePort {

        void saveArticle(Article article);

        Page<Article> getAllArticles(Pageable pageable);

        Article getArticle(Long id);

        void updateArticle(Article article);

        void deleteArticle(Long id);

//        Page<Article> getAllArticles(Pageable pageable);


}