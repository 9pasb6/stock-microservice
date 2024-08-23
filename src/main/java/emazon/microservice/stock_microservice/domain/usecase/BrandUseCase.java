package emazon.microservice.stock_microservice.domain.usecase;

import emazon.microservice.stock_microservice.domain.api.IBrandServicePort;
import emazon.microservice.stock_microservice.domain.model.Brand;
import emazon.microservice.stock_microservice.domain.spi.IBrandPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

public class BrandUseCase implements IBrandServicePort {

    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public Brand save(Brand brand) {
        validateBrand(brand); // Validar la marca antes de guardar

        // Verificar si ya existe una marca con el mismo nombre
        if (brandPersistencePort.existsByName(brand.getName())) {
            throw new IllegalArgumentException("Brand name already exists");
        }

        return brandPersistencePort.save(brand);
    }

    @Override
    public Page<Brand> findAll(Pageable pageable) {

        Page<Brand> brands = this.brandPersistencePort.findAll(pageable);


        return brands;
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
        validateBrand(brand); // Validar la marca antes de actualizar

        // Verificar si el nombre de la marca se está cambiando y si ya existe otra marca con el mismo nombre
        Brand existingBrand = brandPersistencePort.findById(brand.getId());
        if (existingBrand != null && !Objects.equals(existingBrand.getName(), brand.getName()) &&
                brandPersistencePort.existsByName(brand.getName())) {
            throw new IllegalArgumentException("Brand name already exists");
        }

        brandPersistencePort.update(brand);
    }

    @Override
    public Brand getBrandByName(String name) {
        return null;
    }

    @Override
    public String getBrandNameById(Long id) {
        return "";
    }

    // Método para validar la marca
    private void validateBrand(Brand brand) {
        if (brand.getName() == null || brand.getName().isEmpty()) {
            throw new IllegalArgumentException("Brand name cannot be null or empty");
        }
        if (brand.getDescription() == null || brand.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Brand description cannot be null or empty");
        }
        if (brand.getName().length() > 50) {
            throw new IllegalArgumentException("Brand name must be at most 50 characters long");
        }
        if (brand.getDescription().length() > 120) {
            throw new IllegalArgumentException("Brand description must be at most 120 characters long");
        }
    }


}