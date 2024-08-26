package emazon.microservice.stock_microservice.domain.spi;

import emazon.microservice.stock_microservice.domain.model.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBrandPersistencePort {

    Brand save(Brand brand);

    List<Brand> findAll(String order);

    Brand findById(Long id);

    void delete(Long id);

    void deleteAll();

    void update(Brand brand);

    Brand getBrandByName(String brandName);

    boolean existsByName(String name);

    boolean existsById(Long id);
}