package emazon.microservice.stock_microservice.domain.exceptions;

public class BrandExceptions {

    private BrandExceptions() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static class BrandNameAlreadyExistsException extends RuntimeException {
        public BrandNameAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class BrandNotFoundException extends RuntimeException {
        public BrandNotFoundException(String message) {
            super(message);
        }
    }
}