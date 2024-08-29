package emazon.microservice.stock_microservice.domain.util;

public class ErrorMessages {


    private ErrorMessages() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static final String BRAND_NOT_FOUND = "The brand with ID ";
    public static final String BRAND_NOT_FOUND_SUFFIX = " was not found.";
    public static final String BRAND_NAME_ALREADY_EXISTS = "The brand name already exists.";
    public static final String BRAND_NAME_NOT_FOUND = "The brand with name ";


    public static final String CATEGORY_NOT_FOUND = "Category not found with ID: ";
    public static final String CATEGORY_NAME_ALREADY_EXISTS = "The category name already exists.";
    public static final String CATEGORIES_NOT_FOUND_FOR_IDS = "Categories not found for IDs: ";
    public static final String CATEGORY_NAMES_NOT_FOUND_FOR_IDS = "Category names not found for IDs: ";
    public static final String SOME_CATEGORY_NAMES_NOT_FOUND_FOR_IDS = "Some category names not found for IDs: ";
    public static final String CATEGORY_NAME_NOT_FOUND = "The category with name ";
    public static final String CATEGORY_NOT_FOUND_SUFFIX = " was not found.";


    public static final String ARTICLE_NAME_ALREADY_EXISTS = "The article name already exists.";
    public static final String ARTICLE_NOT_FOUND = "Article not found with ID: ";
    public static final String BRAND_NOT_FOUND_FOR_ARTICLE = "The brand '";
    public static final String CATEGORY_NOT_FOUND_FOR_ARTICLE = "The category '";
    public static final String DOES_NOT_EXIST = "' does not exist.";
}