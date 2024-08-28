package emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.repository;

import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    boolean existsByName(String name);
    boolean existsByIdIn(Set<Long> ids);
}
