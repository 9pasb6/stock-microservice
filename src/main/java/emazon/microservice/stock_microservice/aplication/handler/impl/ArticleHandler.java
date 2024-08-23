package emazon.microservice.stock_microservice.aplication.handler.impl;

import emazon.microservice.stock_microservice.aplication.dto.request.ArticleRequest;
import emazon.microservice.stock_microservice.aplication.dto.response.ArticleResponse;
import emazon.microservice.stock_microservice.aplication.handler.IArticleHandler;
import emazon.microservice.stock_microservice.aplication.mapper.request.ArticleRequestMapper;
import emazon.microservice.stock_microservice.aplication.mapper.response.ArticleResponseMapper;
import emazon.microservice.stock_microservice.domain.api.IArticleServicePort;
import emazon.microservice.stock_microservice.domain.api.IBrandServicePort;
import emazon.microservice.stock_microservice.domain.api.ICategoryServicePort;
import emazon.microservice.stock_microservice.domain.model.Article;
import emazon.microservice.stock_microservice.domain.model.Category;
import emazon.microservice.stock_microservice.domain.model.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Page<ArticleResponse> getAllArticles(Pageable pageable) {
        Page<Article> articles = articleServicePort.getAllArticles(pageable);
        return articles.map(articleResponseMapper::articleToArticleResponse);
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
    public void deleteArticle(Long id) {
        articleServicePort.deleteArticle(id);
    }

}