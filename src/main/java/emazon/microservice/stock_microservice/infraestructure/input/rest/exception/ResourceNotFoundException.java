package emazon.microservice.stock_microservice.infraestructure.input.rest.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}