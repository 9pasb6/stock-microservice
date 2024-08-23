package emazon.microservice.stock_microservice.domain.usecase;

import emazon.microservice.stock_microservice.domain.api.IBrandServicePort;
import emazon.microservice.stock_microservice.domain.model.Brand;
import emazon.microservice.stock_microservice.domain.spi.IBrandPersistencePort;

import java.util.List;

public class BrandUseCase implements IBrandServicePort {

    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public List<Brand> findAll() {

        return brandPersistencePort.findAll();
    }

    @Override
    public Brand save(Brand brand) {

        return brandPersistencePort.save(brand);
    }

    @Override
    public Brand findById(Long id) {

        return brandPersistencePort.findById(id);
    }

    @Override
    public void delete(Long id) {

        brandPersistencePort.delete(id);
    }

    @Override
    public void deleteAll() {

        brandPersistencePort.deleteAll();
    }

    @Override
    public void update(Brand brand) {

        brandPersistencePort.update(brand);
    }

    @Override
    public Brand getBrandByName(String name) {
        return null;
    }

    @Override
    public String getBrandNameById(Long id) {
        return null;
    }
}
