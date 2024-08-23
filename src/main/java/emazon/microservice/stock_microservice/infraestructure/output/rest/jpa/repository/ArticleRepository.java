package emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.repository;

import emazon.microservice.stock_microservice.domain.model.Article;
import emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ArticleRepository extends JpaRepository <ArticleEntity, Long> {

    Page<ArticleEntity> findAll(Pageable pageable);
    boolean existsByName(String name);



}
