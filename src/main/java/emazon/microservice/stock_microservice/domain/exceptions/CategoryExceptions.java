package emazon.microservice.stock_microservice.domain.exceptions;

public class CategoryExceptions {

    private CategoryExceptions() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static class CategoryNameAlreadyExistsException extends RuntimeException {
        public CategoryNameAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class CategoryNotFoundException extends RuntimeException {
        public CategoryNotFoundException(String message) {
            super(message);
        }
    }
}