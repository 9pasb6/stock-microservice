package emazon.microservice.stock_microservice.domain.usecase;

import emazon.microservice.stock_microservice.domain.api.IBrandServicePort;
import emazon.microservice.stock_microservice.domain.exceptions.BrandExceptions;
import emazon.microservice.stock_microservice.domain.model.Brand;
import emazon.microservice.stock_microservice.domain.spi.IBrandPersistencePort;
import emazon.microservice.stock_microservice.domain.util.ErrorMessages;
import emazon.microservice.stock_microservice.domain.util.ValidationUtils;

import java.util.List;
import java.util.Objects;

public class BrandUseCase implements IBrandServicePort {

    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public Brand save(Brand brand) {
        ValidationUtils.validateBrand(brand);

        if (brandPersistencePort.existsByName(brand.getName())) {
            throw new BrandExceptions.BrandNameAlreadyExistsException(ErrorMessages.BRAND_NAME_ALREADY_EXISTS);
        }

        return brandPersistencePort.save(brand);
    }

    @Override
    public List<Brand> findAll(String  order) {
        return this.brandPersistencePort.findAll(order);
    }

    @Override
    public Brand findById(Long id) {
        Brand brand = brandPersistencePort.findById(id);
        if (brand == null) {
            throw new BrandExceptions.BrandNotFoundException(ErrorMessages.BRAND_NOT_FOUND + id + ErrorMessages.BRAND_NOT_FOUND_SUFFIX);
        }
        return brand;
    }

    @Override
    public void delete(Long id) {
        if (!brandPersistencePort.existsById(id)) {
            throw new BrandExceptions.BrandNotFoundException(ErrorMessages.BRAND_NOT_FOUND + id +  ErrorMessages.BRAND_NOT_FOUND_SUFFIX);
        }
        brandPersistencePort.delete(id);
    }

    @Override
    public void deleteAll() {
        brandPersistencePort.deleteAll();
    }

    @Override
    public void update(Brand brand) {
        ValidationUtils.validateBrand(brand);

        Brand existingBrand = brandPersistencePort.findById(brand.getId());
        if (existingBrand == null) {
            throw new BrandExceptions.BrandNotFoundException(
                    ErrorMessages.BRAND_NOT_FOUND + brand.getId() +  ErrorMessages.BRAND_NOT_FOUND_SUFFIX
            );
        }

        if (!Objects.equals(existingBrand.getName(), brand.getName()) &&
                brandPersistencePort.existsByName(brand.getName())) {
            throw new BrandExceptions.BrandNameAlreadyExistsException(ErrorMessages.BRAND_NAME_ALREADY_EXISTS);
        }

        brandPersistencePort.update(brand);
    }

    @Override
    public Brand getBrandByName(String name) {
        Brand brand = brandPersistencePort.getBrandByName(name);
        if (brand == null) {
            throw new BrandExceptions.BrandNotFoundException(ErrorMessages.BRAND_NAME_NOT_FOUND + name + ErrorMessages.BRAND_NOT_FOUND_SUFFIX);
        }
        return brand;
    }

    @Override
    public String getBrandNameById(Long id) {
        Brand brand = findById(id);
        return brand.getName();
    }
}