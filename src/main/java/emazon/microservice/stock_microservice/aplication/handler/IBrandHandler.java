package emazon.microservice.stock_microservice.aplication.handler;

import emazon.microservice.stock_microservice.aplication.dto.request.BrandRequest;
import emazon.microservice.stock_microservice.aplication.dto.response.BrandResponse;

import java.util.List;

public interface IBrandHandler {

    void saveBrand(BrandRequest brandRequest);

    List<BrandResponse> getAllBrands();

    BrandResponse getBrand(Long brandId);

    void updateBrand(BrandRequest brandRequest);

    void deleteBrand(Long brandId);
}