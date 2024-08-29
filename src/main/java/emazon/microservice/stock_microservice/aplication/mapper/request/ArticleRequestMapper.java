package emazon.microservice.stock_microservice.aplication.mapper.request;

import emazon.microservice.stock_microservice.aplication.dto.request.ArticleRequest;
import emazon.microservice.stock_microservice.domain.model.Article;
import emazon.microservice.stock_microservice.domain.model.Brand;
import emazon.microservice.stock_microservice.domain.model.Category;
import org.mapstruct.*;

import java.util.List;


@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ArticleRequestMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "stockQuantity", target = "stockQuantity")
    @Mapping(source = "brandId", target = "brand", qualifiedByName = "brandIdToBrand")
    @Mapping(source = "categoryIds", target = "categories", qualifiedByName = "categoryIdsToCategories")
    Article requestToArticle(ArticleRequest request);

    @Named("brandIdToBrand")
    default Brand mapBrandIdToBrand(Long brandId) {
        return new Brand(brandId, null, null);
    }

    @Named("categoryIdsToCategories")
    default List<Category> mapCategoryIdsToCategories(List<Long> categoryIds) {
        return categoryIds.stream()
                .map(id -> new Category(id, null, null)).toList();
    }
}