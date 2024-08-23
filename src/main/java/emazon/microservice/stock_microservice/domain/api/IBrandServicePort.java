package emazon.microservice.stock_microservice.domain.api;

import emazon.microservice.stock_microservice.domain.model.Brand;

import java.util.List;

public interface IBrandServicePort {
    List<Brand> findAll();
    Brand save(Brand brand);
    Brand findById(Long id);
    void delete(Long id);
    void deleteAll();
    void update(Brand brand);
    Brand getBrandByName(String name);
    String getBrandNameById (Long id);

}
