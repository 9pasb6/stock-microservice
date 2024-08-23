package emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.mapper;


import emazon.microservice.stock_microservice.domain.model.Category;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ICategoryEntityMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    Category categoryEntityToCategory(CategoryEntity categoryEntity);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    CategoryEntity categoryToCategoryEntity(Category category);
}