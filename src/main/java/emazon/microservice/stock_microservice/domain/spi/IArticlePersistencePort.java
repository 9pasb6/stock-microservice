package emazon.microservice.stock_microservice.domain.spi;

import emazon.microservice.stock_microservice.domain.model.Article;
import emazon.microservice.stock_microservice.domain.model.Brand;
import emazon.microservice.stock_microservice.domain.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface IArticlePersistencePort {

    void saveArticle(Article article);

    Page<Article> getAllArticles(Pageable pageable);

    Article getArticle(Long articleId);

    void updateArticle(Article article);

    void deleteArticle(Long articleId);

    boolean existsByName(String name);

//    Page<Article> getAllArticles(Pageable pageable);

}