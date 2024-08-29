package emazon.microservice.stock_microservice.domain.util;

import emazon.microservice.stock_microservice.domain.model.Article;
import emazon.microservice.stock_microservice.domain.model.Brand;
import emazon.microservice.stock_microservice.domain.model.Category;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ValidationUtils {

    public static void validateArticle(Article article) {

        if (article.getName() == null || article.getName().isEmpty()) {
            throw new IllegalArgumentException("The article name cannot be empty or null.");
        }
        if (article.getDescription() == null || article.getDescription().isEmpty()) {
            throw new IllegalArgumentException("The article description cannot be empty or null.");
        }
        if (article.getPrice() == null || article.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("The article price must be greater than zero.");
        }
        if (article.getStockQuantity() == null || article.getStockQuantity() <= 0) {
            throw new IllegalArgumentException("The article stock quantity cannot be negative.");
        }
        if (article.getBrandId() == null) {
            throw new IllegalArgumentException("The article must belong to a brand.");
        }
        if (article.getCategories() == null || article.getCategories().isEmpty() || article.getCategories().size() > 3) {
            throw new IllegalArgumentException("The article must have between 1 and 3 categories.");
        }
        Set<Category> uniqueCategories = new HashSet<>(article.getCategories());
        if (uniqueCategories.size() < article.getCategories().size()) {
            throw new IllegalArgumentException("The article cannot have duplicate categories.");
        }
    }

    public static void validateBrand(Brand brand) {
        if (brand.getName() == null || brand.getName().isEmpty()) {
            throw new IllegalArgumentException("The brand name cannot be empty or null.");
        }
        if (brand.getDescription() == null || brand.getDescription().isEmpty()) {
            throw new IllegalArgumentException("The brand description cannot be empty or null.");
        }
        if (brand.getName().length() > 50) {
            throw new IllegalArgumentException("The brand name must be at most 50 characters long.");
        }
        if (brand.getDescription().length() > 120) {
            throw new IllegalArgumentException("The brand description must be at most 120 characters long.");
        }
    }

    public static void validateCategory(Category category) {
        if (category.getName() == null || category.getName().isEmpty()) {
            throw new IllegalArgumentException("The category name cannot be empty or null.");
        }
        if (category.getDescription() == null || category.getDescription().isEmpty()) {
            throw new IllegalArgumentException("The category description cannot be empty or null.");
        }
        if (category.getName().length() > 50) {
            throw new IllegalArgumentException("The category name must be at most 50 characters long.");
        }
        if (category.getDescription().length() > 90) {
            throw new IllegalArgumentException("The category description must be at most 90 characters long.");
        }
    }
}