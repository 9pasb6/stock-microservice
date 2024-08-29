package emazon.microservice.stock_microservice.usecase;

import emazon.microservice.stock_microservice.domain.exceptions.BrandExceptions;
import emazon.microservice.stock_microservice.domain.model.Brand;
import emazon.microservice.stock_microservice.domain.spi.IBrandPersistencePort;
import emazon.microservice.stock_microservice.domain.usecase.BrandUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrandUseCaseTest {

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private BrandUseCase brandUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testSaveBrand_NameAlreadyExistsException() {
        Brand brand = new Brand();
        brand.setName("Existing Brand");
        brand.setDescription("Description");

        when(brandPersistencePort.existsByName(brand.getName())).thenReturn(true);

        BrandExceptions.BrandNameAlreadyExistsException exception = assertThrows(
                BrandExceptions.BrandNameAlreadyExistsException.class,
                () -> brandUseCase.save(brand)
        );
        assertEquals("The brand name already exists.", exception.getMessage());
    }

    @Test
    void testSaveBrand_ThrowsExceptionWhenNameIsNull() {
        Brand brand = new Brand();
        brand.setDescription("Description");

        assertThrows(IllegalArgumentException.class, () -> {
            brandUseCase.save(brand);
        }, "El nombre de la marca no puede estar vacío o ser nulo");
    }

    @Test
    void testSaveBrand_ThrowsExceptionWhenDescriptionIsNull() {
        Brand brand = new Brand();
        brand.setName("New Brand");

        assertThrows(IllegalArgumentException.class, () -> {
            brandUseCase.save(brand);
        }, "La descripción de la marca no puede estar vacía o ser nula");
    }

    @Test
    void testSaveBrand_ThrowsExceptionWhenNameIsTooLong() {
        Brand brand = new Brand();
        brand.setName("A".repeat(51)); // 51 characters
        brand.setDescription("Description");

        assertThrows(IllegalArgumentException.class, () -> {
            brandUseCase.save(brand);
        }, "El nombre de la marca debe tener como máximo 50 caracteres");
    }

    @Test
    void testSaveBrand_ThrowsExceptionWhenDescriptionIsTooLong() {
        Brand brand = new Brand();
        brand.setName("New Brand");
        brand.setDescription("A".repeat(121)); // 121 characters

        assertThrows(IllegalArgumentException.class, () -> {
            brandUseCase.save(brand);
        }, "La descripción de la marca debe tener como máximo 120 caracteres");
    }

    @Test
    void testUpdateBrand_NameAlreadyExistsException() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Updated Brand");
        brand.setDescription("Updated Description");

        Brand existingBrand = new Brand();
        existingBrand.setName("Existing Brand");

        when(brandPersistencePort.findById(brand.getId())).thenReturn(existingBrand);
        when(brandPersistencePort.existsByName(brand.getName())).thenReturn(true);

        BrandExceptions.BrandNameAlreadyExistsException exception = assertThrows(
                BrandExceptions.BrandNameAlreadyExistsException.class,
                () -> brandUseCase.update(brand)
        );
        assertEquals("The brand name already exists.", exception.getMessage());
    }

    @Test
    void testUpdateBrand_Success() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Updated Brand");
        brand.setDescription("Updated Description");

        Brand existingBrand = new Brand();
        existingBrand.setName("Old Brand");

        when(brandPersistencePort.findById(brand.getId())).thenReturn(existingBrand);
        when(brandPersistencePort.existsByName(brand.getName())).thenReturn(false);

        brandUseCase.update(brand);
        verify(brandPersistencePort, times(1)).update(brand);
    }

    // CRUD

    @Test
    void testFindAllBrands() {
        List<Brand> brands = new ArrayList<>();
        when(brandPersistencePort.findAll("asc")).thenReturn(brands);

        List<Brand> result = brandUseCase.findAll("asc");
        assertEquals(brands, result);
    }

    @Test
    void testFindBrandById() {
        Brand brand = new Brand();
        brand.setId(1L);

        when(brandPersistencePort.findById(1L)).thenReturn(brand);

        Brand result = brandUseCase.findById(1L);
        assertEquals(brand, result);
    }

    @Test
    void testFindBrandById_BrandNotFoundException() {
        when(brandPersistencePort.findById(1L)).thenReturn(null);

        BrandExceptions.BrandNotFoundException exception = assertThrows(
                BrandExceptions.BrandNotFoundException.class,
                () -> brandUseCase.findById(1L)
        );
        assertEquals("The brand with ID 1 was not found.", exception.getMessage());
    }

    @Test
    void testDeleteBrandById() {
        when(brandPersistencePort.existsById(1L)).thenReturn(true);
        brandUseCase.delete(1L);
        verify(brandPersistencePort, times(1)).delete(1L);
    }

    @Test
    void testDeleteAllBrands() {
        brandUseCase.deleteAll();
        verify(brandPersistencePort, times(1)).deleteAll();
    }

    @Test
    void testGetBrandByName_BrandNotFoundException() {
        when(brandPersistencePort.getBrandByName("NonExistentBrand")).thenReturn(null);

        BrandExceptions.BrandNotFoundException exception = assertThrows(
                BrandExceptions.BrandNotFoundException.class,
                () -> brandUseCase.getBrandByName("NonExistentBrand")
        );
        assertEquals("The brand with name NonExistentBrand was not found.", exception.getMessage());
    }

    @Test
    void testGetBrandNameById() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Brand Name");

        when(brandPersistencePort.findById(1L)).thenReturn(brand);

        String result = brandUseCase.getBrandNameById(1L);
        assertEquals("Brand Name", result);
    }
}