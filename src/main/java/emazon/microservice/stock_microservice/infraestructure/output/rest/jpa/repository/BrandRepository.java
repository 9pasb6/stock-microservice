package emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.repository;

import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {

    boolean existsByName(String name);
    boolean existsById(Long id);
}
