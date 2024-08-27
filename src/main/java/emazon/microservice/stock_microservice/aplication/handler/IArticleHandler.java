package emazon.microservice.stock_microservice.aplication.handler;


import emazon.microservice.stock_microservice.aplication.dto.request.ArticleRequest;
import emazon.microservice.stock_microservice.aplication.dto.response.ArticleResponse;

import java.util.List;

public interface IArticleHandler {


    void saveArticle(ArticleRequest articleRequest);

    List<ArticleResponse> getAllArticles(String order);


    ArticleResponse getArticle(Long id);

    void updateArticle(ArticleRequest articleRequest);

    boolean deleteArticle(Long id);

    List<ArticleResponse> getAllByBrandName(String brandName, String order);

    List<ArticleResponse> getAllByCategoryName(String categoryName, String order); // Nuevo método




}
