package emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.repository;

import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    boolean existsByName(String name);

    @Query("SELECT a FROM ArticleEntity a JOIN a.categories c WHERE c.name = :categoryName")
    Page<ArticleEntity> findByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);

    @Query("SELECT a FROM ArticleEntity a JOIN a.brand c WHERE c.name = :brandName")
    Page<ArticleEntity> findByBrandName(@Param("brandName") String brandName, Pageable pageable);


}