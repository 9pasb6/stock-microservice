package emazon.microservice.stock_microservice.aplication.handler;

import emazon.microservice.stock_microservice.aplication.dto.request.BrandRequest;
import emazon.microservice.stock_microservice.aplication.dto.response.ArticleResponse;
import emazon.microservice.stock_microservice.aplication.dto.response.BrandResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBrandHandler {

    void saveBrand(BrandRequest brandRequest);

    Page<BrandResponse> getAllBrands(Pageable pageable);
//    Page<ArticleResponse> getAllArticles();

    BrandResponse getBrand(Long brandId);

    void updateBrand(BrandRequest brandRequest);

    void deleteBrand(Long brandId);
}