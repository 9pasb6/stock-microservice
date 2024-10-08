package emazon.microservice.stock_microservice.aplication.handler.impl;

import emazon.microservice.stock_microservice.aplication.dto.request.BrandRequest;
import emazon.microservice.stock_microservice.aplication.dto.response.BrandResponse;
import emazon.microservice.stock_microservice.aplication.handler.IBrandHandler;
import emazon.microservice.stock_microservice.aplication.mapper.request.BrandRequestMapper;
import emazon.microservice.stock_microservice.aplication.mapper.response.BrandResponseMapper;
import emazon.microservice.stock_microservice.domain.api.IBrandServicePort;
import emazon.microservice.stock_microservice.domain.model.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class BrandHandler implements IBrandHandler {

    private final IBrandServicePort brandServicePort;
    private final BrandRequestMapper brandRequestMapper;
    private final BrandResponseMapper brandResponseMapper;

    @Override
    public void saveBrand(BrandRequest brandRequest) {
        Brand brand = brandRequestMapper.requestToBrand(brandRequest);
        brandServicePort.save(brand);
    }



    @Override
    public List<BrandResponse> getAllBrands(String order) {
        List<Brand> brands = brandServicePort.findAll(order);
        return brands.stream()
                .map(brandResponseMapper::brandToResponse).toList();
    }


    @Override
    public BrandResponse getBrand(Long brandId) {
        Brand brand = brandServicePort.findById(brandId);
        return brandResponseMapper.brandToResponse(brand);
    }

    @Override
    public boolean updateBrand(BrandRequest brandRequest) {
        Brand brand = brandRequestMapper.requestToBrand(brandRequest);
        brandServicePort.update(brand);
        return false;
    }

    @Override
    public boolean deleteBrand(Long brandId) {
        brandServicePort.delete(brandId);
        return false;
    }
}