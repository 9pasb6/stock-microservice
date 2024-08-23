package emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.adapter;

import emazon.microservice.stock_microservice.domain.model.Article;
import emazon.microservice.stock_microservice.domain.model.Brand;
import emazon.microservice.stock_microservice.domain.spi.IBrandPersistencePort;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.mapper.IBrandEntityMapper;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class BrandJpaAdapter implements IBrandPersistencePort {

    private final BrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;

    @Override
    public Page<Brand> findAll(Pageable pageable) {
        return brandRepository.findAll(pageable)
                .map(brandEntityMapper::brandEntityToBrand);
    }


    @Override
    public Brand save(Brand brand) {
        System.out.println("Saving brand: " + brand.getName());
        return brandEntityMapper.brandEntityToBrand(brandRepository.save(brandEntityMapper.brandToBrandEntity(brand)));
    }

    @Override
    public Brand findById(Long id) {

        return brandRepository.findById(id)
                .map(brandEntityMapper::brandEntityToBrand)
                .orElse(null);
    }

    @Override
    public void delete(Long id) {
        brandRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        brandRepository.deleteAll();
    }

    @Override
    public void update(Brand brand) {
        if (brandRepository.existsById(brand.getId())) {
            brandRepository.save(brandEntityMapper.brandToBrandEntity(brand));
        }
    }

    @Override
    public Brand getBrandByName(String name) {
        return brandRepository.findAll().stream()
                .filter(brandEntity -> brandEntity.getName().equals(name))
                .map(brandEntityMapper::brandEntityToBrand)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean existsByName(String name) {
        return brandRepository.existsByName(name);
    }


}