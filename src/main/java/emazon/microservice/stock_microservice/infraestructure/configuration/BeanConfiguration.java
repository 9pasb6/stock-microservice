package emazon.microservice.stock_microservice.infraestructure.configuration;

import emazon.microservice.stock_microservice.domain.api.IArticleServicePort;
import emazon.microservice.stock_microservice.domain.api.IBrandServicePort;
import emazon.microservice.stock_microservice.domain.api.ICategoryServicePort;
import emazon.microservice.stock_microservice.domain.spi.IArticlePersistencePort;
import emazon.microservice.stock_microservice.domain.spi.IBrandPersistencePort;
import emazon.microservice.stock_microservice.domain.spi.ICategoryPersistencePort;
import emazon.microservice.stock_microservice.domain.usecase.ArticleUseCase;
import emazon.microservice.stock_microservice.domain.usecase.BrandUseCase;
import emazon.microservice.stock_microservice.domain.usecase.CategoryUseCase;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.adapter.ArticleJpaAdapter;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.adapter.BrandJpaAdapter;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.adapter.CategoryJpaAdapter;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.mapper.IArticleEntityMapper;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.mapper.IBrandEntityMapper;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.mapper.ICategoryEntityMapper;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.repository.ArticleRepository;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.repository.BrandRepository;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.repository.CategoryRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public IArticlePersistencePort articlePersistencePort(ArticleRepository articleRepository, IArticleEntityMapper articleEntityMapper) {
        return new ArticleJpaAdapter(articleRepository, articleEntityMapper);
    }

    @Bean
    public ICategoryPersistencePort categoryPersistencePort(CategoryRepository categoryRepository, ICategoryEntityMapper categoryEntityMapper) {
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public IBrandPersistencePort brandPersistencePort(BrandRepository brandRepository, IBrandEntityMapper brandEntityMapper) {
        return new BrandJpaAdapter(brandRepository, brandEntityMapper);
    }

    @Bean
    public IArticleServicePort articleServicePort(IArticlePersistencePort articlePersistencePort, IBrandPersistencePort brandPersistencePort, ICategoryPersistencePort categoryPersistencePort) {
        return new ArticleUseCase(articlePersistencePort,  brandPersistencePort, categoryPersistencePort);
    }

    @Bean
    public ICategoryServicePort categoryServicePort(ICategoryPersistencePort categoryPersistencePort) {
        return new CategoryUseCase(categoryPersistencePort);
    }

    @Bean
    public IBrandServicePort brandServicePort(IBrandPersistencePort brandPersistencePort) {
        return new BrandUseCase(brandPersistencePort);
    }
}