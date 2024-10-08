package emazon.microservice.stock_microservice.aplication.handler.impl;

import emazon.microservice.stock_microservice.aplication.dto.request.ArticleRequest;
import emazon.microservice.stock_microservice.aplication.dto.response.ArticleResponse;
import emazon.microservice.stock_microservice.aplication.handler.IArticleHandler;
import emazon.microservice.stock_microservice.aplication.mapper.request.ArticleRequestMapper;
import emazon.microservice.stock_microservice.aplication.mapper.response.ArticleResponseMapper;
import emazon.microservice.stock_microservice.domain.api.IArticleServicePort;
import emazon.microservice.stock_microservice.domain.model.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ArticleHandler implements IArticleHandler {

    private final IArticleServicePort articleServicePort;
    private final ArticleRequestMapper articleRequestMapper;
    private final ArticleResponseMapper articleResponseMapper;

    @Override
    public void saveArticle(ArticleRequest articleRequest) {
        Article article = articleRequestMapper.requestToArticle(articleRequest);
        articleServicePort.saveArticle(article);
    }

    @Override
    public List<ArticleResponse> getAllArticles(String order) {
        List<Article> articles = articleServicePort.getAllArticles(order);
        return articles.stream()
                .map(articleResponseMapper::articleToArticleResponse).toList();
    }

    @Override
    public ArticleResponse getArticle(Long id) {
        Article article = articleServicePort.getArticle(id);
        return articleResponseMapper.articleToArticleResponse(article);
    }

    @Override
    public void updateArticle(ArticleRequest articleRequest) {
        Article article = articleRequestMapper.requestToArticle(articleRequest);
        articleServicePort.updateArticle(article);
    }

    @Override
    public boolean deleteArticle(Long id) {
        articleServicePort.deleteArticle(id);
        return false;
    }

    @Override
    public List<ArticleResponse> getAllByBrandName(String brandName, String order) {
        List<Article> articles = articleServicePort.getAllByBrandName(brandName, order);
        return articles.stream()
                .map(articleResponseMapper::articleToArticleResponse).toList();
    }

    @Override
    public List<ArticleResponse> getAllByCategoryName(String categoryName, String order) {
        List<Article> articles = articleServicePort.getAllByCategoryName(categoryName, order);
        return articles.stream()
                .map(articleResponseMapper::articleToArticleResponse).toList();
    }

}