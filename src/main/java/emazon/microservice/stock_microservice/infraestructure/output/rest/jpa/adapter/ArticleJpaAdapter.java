package emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.adapter;

import emazon.microservice.stock_microservice.domain.model.Article;
import emazon.microservice.stock_microservice.domain.spi.IArticlePersistencePort;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.entity.ArticleEntity;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.entity.BrandEntity;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.mapper.IArticleEntityMapper;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ArticleJpaAdapter implements IArticlePersistencePort {

    private final ArticleRepository articleRepository;
    private final IArticleEntityMapper articleEntityMapper;

    @Override
    public void saveArticle(Article article) {
        articleRepository.save(articleEntityMapper.articleToArticleEntity(article));
    }

    @Override
    public List<Article> getAllArticles(String order) {
        Pageable pageable = createPageRequest(order);
        Page<ArticleEntity> page = articleRepository.findAll(pageable);
        return page.stream()
                .map(articleEntityMapper::articleEntityToArticle)
                .collect(Collectors.toList());
    }



    @Override
    public Article getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(articleEntityMapper::articleEntityToArticle)
                .orElse(null);
    }

    @Override
    public void updateArticle(Article article) {
        if (articleRepository.existsById(article.getId())) {
            articleRepository.save(articleEntityMapper.articleToArticleEntity(article));
        }
    }

    @Override
    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    @Override
    public boolean existsByName(String name) {
        return articleRepository.existsByName(name);
    }

    @Override
    public boolean existsById(Long id) {
        return articleRepository.existsById(id);
    }

    @Override
    public List<Article> findAllByBrandName(String brandName, String order) {
        Pageable pageable = createPageRequest(order);
        return articleRepository.findByBrandName(brandName, pageable).stream()
                .map(articleEntityMapper::articleEntityToArticle)
                .collect(Collectors.toList());
    }

    @Override
    public List<Article> findAllByCategoryName(String categoryName, String order) {
        Pageable pageable = createPageRequest(order);
        return articleRepository.findByCategoryName(categoryName, pageable).stream()
                .map(articleEntityMapper::articleEntityToArticle)
                .collect(Collectors.toList());
    }



    private Pageable createPageRequest(String order) {
        Sort sort = "desc".equalsIgnoreCase(order) ? Sort.by("name").descending() : Sort.by("name").ascending();
        return PageRequest.of(0, 10, sort);
    }
}