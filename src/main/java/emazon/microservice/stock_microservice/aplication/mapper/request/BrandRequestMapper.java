package emazon.microservice.stock_microservice.aplication.mapper.request;

import emazon.microservice.stock_microservice.aplication.dto.request.BrandRequest;
import emazon.microservice.stock_microservice.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface BrandRequestMapper {


    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    Brand requestToBrand(BrandRequest brandRequest);
}