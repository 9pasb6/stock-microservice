package emazon.microservice.stock_microservice.aplication.handler;

import emazon.microservice.stock_microservice.aplication.dto.request.BrandRequest;
import emazon.microservice.stock_microservice.aplication.dto.response.BrandResponse;

import java.util.List;

public interface IBrandHandler {

    void saveBrand(BrandRequest brandRequest);

    List<BrandResponse> getAllBrands(String order);


    BrandResponse getBrand(Long brandId);

    boolean updateBrand(BrandRequest brandRequest);

    boolean deleteBrand(Long brandId);
}