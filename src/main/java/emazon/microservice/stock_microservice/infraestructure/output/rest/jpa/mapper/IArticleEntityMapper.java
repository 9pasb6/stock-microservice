package emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.mapper;

import emazon.microservice.stock_microservice.domain.model.Article;
import emazon.microservice.stock_microservice.domain.model.Brand;
import emazon.microservice.stock_microservice.domain.model.Category;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.entity.ArticleEntity;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.entity.BrandEntity;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.entity.CategoryEntity;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.repository.BrandRepository;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.repository.CategoryRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.Set;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IArticleEntityMapper {

    @Mapping(source = "categories", target = "categories", qualifiedByName = "categoryEntitiesToCategories")
    @Mapping(source = "brand", target = "brand", qualifiedByName = "brandEntityToBrand")
    Article articleEntityToArticle(ArticleEntity articleEntity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "stockQuantity", target = "stockQuantity")
    @Mapping(source = "brand", target = "brand", qualifiedByName = "brandToBrandEntity")
    @Mapping(source = "categories", target = "categories", qualifiedByName = "categoriesToCategoryEntities")
    ArticleEntity articleToArticleEntity(Article article);

    @Named("categoryEntitiesToCategories")
    default Set<Category> mapCategoryEntitiesToCategories(Set<CategoryEntity> categoryEntities) {
        return categoryEntities.stream()
                .map(categoryEntity -> new Category(categoryEntity.getId(), categoryEntity.getName(), categoryEntity.getDescription()))
                .collect(Collectors.toSet());
    }

    @Named("categoriesToCategoryEntities")
    default Set<CategoryEntity> mapCategoriesToCategoryEntities(Set<Category> categories) {
        return categories.stream()
                .map(category -> {
                    CategoryEntity categoryEntity = new CategoryEntity();
                    categoryEntity.setId(category.getId());
                    categoryEntity.setName(category.getName());
                    categoryEntity.setDescription(category.getDescription());
                    return categoryEntity;
                })
                .collect(Collectors.toSet());
    }

    @Named("brandEntityToBrand")
    default Brand mapBrandEntityToBrand(BrandEntity brandEntity) {
        if (brandEntity == null) {
            return null;
        }
        return new Brand(brandEntity.getId(), brandEntity.getName(), brandEntity.getDescription()); // Asumiendo que Brand tiene un constructor con id y name
    }

    @Named("brandToBrandEntity")
    default BrandEntity mapBrandToBrandEntity(Brand brand) {
        if (brand == null) {
            return null;
        }
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(brand.getId());
        brandEntity.setName(brand.getName());// Asumiendo que BrandEntity tiene un método setName()
        brandEntity.setDescription(brand.getDescription());
        return brandEntity;
    }
}