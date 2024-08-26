package emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.adapter;

import emazon.microservice.stock_microservice.domain.model.Brand;
import emazon.microservice.stock_microservice.domain.spi.IBrandPersistencePort;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.entity.BrandEntity;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.mapper.IBrandEntityMapper;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class BrandJpaAdapter implements IBrandPersistencePort {

    private final BrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;

    @Override
    public List<Brand> findAll(String order) {
        Pageable pageable = createPageRequest(order);
        Page<BrandEntity> page = brandRepository.findAll(pageable);
        return page.stream()
                .map(brandEntityMapper::brandEntityToBrand)
                .collect(Collectors.toList());
    }

    @Override
    public Brand save(Brand brand) {
        return brandEntityMapper.brandEntityToBrand(
                brandRepository.save(brandEntityMapper.brandToBrandEntity(brand))
        );
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

    @Override
    public boolean existsById(Long id) {
        return brandRepository.existsById(id);
    }

    private Pageable createPageRequest(String order) {
        Sort sort = "desc".equalsIgnoreCase(order) ? Sort.by("name").descending() : Sort.by("name").ascending();
        return PageRequest.of(0, 10, sort);
    }
}