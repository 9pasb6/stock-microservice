package emazon.microservice.stock_microservice.domain.exceptions;

public class ArticleExceptions {

    private ArticleExceptions() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static class BrandNotFoundException extends RuntimeException {
        public BrandNotFoundException(String message) {
            super(message);
        }
    }

    public static class CategoryNotFoundException extends RuntimeException {
        public CategoryNotFoundException(String message) {
            super(message);
        }
    }

    public static class ArticleNameAlreadyExistsException extends RuntimeException {
        public ArticleNameAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class ArticleNotFoundException extends RuntimeException {
        public ArticleNotFoundException(String message) {
            super(message);
        }
    }




}