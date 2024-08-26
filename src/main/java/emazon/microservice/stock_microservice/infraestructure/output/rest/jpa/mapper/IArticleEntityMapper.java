package emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.mapper;

import emazon.microservice.stock_microservice.domain.model.Article;
import emazon.microservice.stock_microservice.domain.model.Brand;
import emazon.microservice.stock_microservice.domain.model.Category;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.entity.ArticleEntity;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.entity.BrandEntity;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.entity.CategoryEntity;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

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
    default List<Category> mapCategoryEntitiesToCategories(List<CategoryEntity> categoryEntities) {
        return categoryEntities.stream()
                .map(categoryEntity -> new Category(categoryEntity.getId(), categoryEntity.getName(), null))
                .collect(Collectors.toList());
    }

    @Named("categoriesToCategoryEntities")
    default List<CategoryEntity> mapCategoriesToCategoryEntities(List<Category> categories) {
        return categories.stream()
                .map(category -> {
                    CategoryEntity categoryEntity = new CategoryEntity();
                    categoryEntity.setId(category.getId());
                    categoryEntity.setName(category.getName());
                    categoryEntity.setDescription(null);
                    return categoryEntity;
                })
                .collect(Collectors.toList());
    }

    @Named("brandEntityToBrand")
    default Brand mapBrandEntityToBrand(BrandEntity brandEntity) {
        if (brandEntity == null) {
            return null;
        }
        return new Brand(brandEntity.getId(), brandEntity.getName(), null);
    }

    @Named("brandToBrandEntity")
    default BrandEntity mapBrandToBrandEntity(Brand brand) {
        if (brand == null) {
            return null;
        }
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(brand.getId());
        brandEntity.setName(brand.getName());
        return brandEntity;
    }
}