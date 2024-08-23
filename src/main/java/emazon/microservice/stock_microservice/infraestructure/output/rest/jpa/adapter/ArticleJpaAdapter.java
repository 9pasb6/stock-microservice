package emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.adapter;

import emazon.microservice.stock_microservice.domain.model.Article;
import emazon.microservice.stock_microservice.domain.model.Brand;
import emazon.microservice.stock_microservice.domain.model.Category;
import emazon.microservice.stock_microservice.domain.spi.IArticlePersistencePort;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.entity.ArticleEntity;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.mapper.IArticleEntityMapper;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.repository.ArticleRepository;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.repository.BrandRepository;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ArticleJpaAdapter implements IArticlePersistencePort {

    private final ArticleRepository articleRepository;


    private final IArticleEntityMapper articleEntityMapper;


    @Override
    public void saveArticle(Article article) {
        System.out.println("Saving article name: " + article.getName());
        System.out.println("Saving article complete: " + article);
        articleRepository.save(articleEntityMapper.articleToArticleEntity(article));
    }


    @Override
    public Page<Article> getAllArticles(Pageable pageable) {
        return articleRepository.findAll(pageable)
                .map(articleEntityMapper::articleEntityToArticle);
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

}