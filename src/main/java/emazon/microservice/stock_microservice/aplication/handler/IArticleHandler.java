package emazon.microservice.stock_microservice.aplication.handler;


import emazon.microservice.stock_microservice.aplication.dto.request.ArticleRequest;
import emazon.microservice.stock_microservice.aplication.dto.response.ArticleResponse;
import emazon.microservice.stock_microservice.domain.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IArticleHandler {


    void saveArticle(ArticleRequest articleRequest);

//    List<ArticleResponse> getAllArticles();
    Page<ArticleResponse> getAllArticles(Pageable pageable);

    ArticleResponse getArticle(Long id);

    void updateArticle(ArticleRequest articleRequest);

    void deleteArticle(Long id);
}
