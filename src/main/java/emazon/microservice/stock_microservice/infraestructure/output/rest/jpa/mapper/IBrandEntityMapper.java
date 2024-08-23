package emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.mapper;

import emazon.microservice.stock_microservice.domain.model.Brand;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.entity.BrandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IBrandEntityMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    Brand brandEntityToBrand(BrandEntity brandEntity);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    BrandEntity brandToBrandEntity(Brand brand);
}