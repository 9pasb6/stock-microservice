package emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.repository;

import emazon.microservice.stock_microservice.domain.model.Category;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    boolean existsByName(String name);
}
