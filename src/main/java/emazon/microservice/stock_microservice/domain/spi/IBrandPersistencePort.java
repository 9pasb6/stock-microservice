package emazon.microservice.stock_microservice.domain.spi;

import emazon.microservice.stock_microservice.domain.model.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBrandPersistencePort {

    // Método para obtener todas las marcas con paginación y ordenación
    Page<Brand> findAll(Pageable pageable);

    // Método para guardar una nueva marca
    Brand save(Brand brand);

    // Método para encontrar una marca por su ID
    Brand findById(Long id);

    // Método para eliminar una marca por su ID
    void delete(Long id);

    // Método para eliminar todas las marcas
    void deleteAll();

    // Método para actualizar una marca existente
    void update(Brand brand);

    // Método para obtener una marca por su nombre
    Brand getBrandByName(String brandName);

    // Método para verificar si ya existe una marca con el mismo nombre
    boolean existsByName(String name);
}