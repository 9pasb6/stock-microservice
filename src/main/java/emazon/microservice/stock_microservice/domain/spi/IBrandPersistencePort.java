package emazon.microservice.stock_microservice.domain.spi;

import emazon.microservice.stock_microservice.domain.model.Brand;

import java.util.List;

public interface IBrandPersistencePort {

    List<Brand> findAll();
    Brand save(Brand brand);
    Brand findById(Long id);
    void delete(Long id);
    void deleteAll();
    void update(Brand brand);
    Brand getBrandByName(String brandName);

}
