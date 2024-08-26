package emazon.microservice.stock_microservice.domain.util;

import emazon.microservice.stock_microservice.domain.model.Article;
import emazon.microservice.stock_microservice.domain.model.Brand;
import emazon.microservice.stock_microservice.domain.model.Category;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ValidationUtils {

    public static void validateArticle(Article article) {
        System.out.println("Categorías antes de la validación: " + article.getCategories());
        if (article.getName() == null || article.getName().isEmpty()) {
            throw new IllegalArgumentException("El nombre del artículo no puede estar vacío o ser nulo");
        }
        if (article.getDescription() == null || article.getDescription().isEmpty()) {
            throw new IllegalArgumentException("La descripción del artículo no puede estar vacía o ser nula");
        }
        if (article.getPrice() == null || article.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio del artículo debe ser mayor que cero");
        }
        if (article.getStockQuantity() == null || article.getStockQuantity() < 0) {
            throw new IllegalArgumentException("La cantidad en stock del artículo no puede ser negativa");
        }
        if (article.getBrandId() == null ) {
            throw new IllegalArgumentException("El artículo debe pertenecer a una marca");
        }
        if (article.getCategories() == null || article.getCategories().isEmpty() || article.getCategories().size() > 3) {
            throw new IllegalArgumentException("El artículo debe tener entre 1 y 3 categorías");
        }
        Set<Category> uniqueCategories = new HashSet<>(article.getCategories());
        if (uniqueCategories.size() < article.getCategories().size()) {
            throw new IllegalArgumentException("El artículo no puede tener categorías repetidas");
        }
    }

    public static void validateBrand(Brand brand) {
        if (brand.getName() == null || brand.getName().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la marca no puede estar vacío o ser nulo");
        }
        if (brand.getDescription() == null || brand.getDescription().isEmpty()) {
            throw new IllegalArgumentException("La descripción de la marca no puede estar vacía o ser nula");
        }
        if (brand.getName().length() > 50) {
            throw new IllegalArgumentException("El nombre de la marca debe tener como máximo 50 caracteres");
        }
        if (brand.getDescription().length() > 120) {
            throw new IllegalArgumentException("La descripción de la marca debe tener como máximo 120 caracteres");
        }
    }

    public static void validateCategory(Category category) {
        if (category.getName() == null || category.getName().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío o ser nulo");
        }
        if (category.getDescription() == null || category.getDescription().isEmpty()) {
            throw new IllegalArgumentException("La descripción de la categoría no puede estar vacía o ser nula");
        }
        if (category.getName().length() > 50) {
            throw new IllegalArgumentException("El nombre de la categoría debe tener como máximo 50 caracteres");
        }
        if (category.getDescription().length() > 90) {
            throw new IllegalArgumentException("La descripción de la categoría debe tener como máximo 90 caracteres");
        }
    }
}