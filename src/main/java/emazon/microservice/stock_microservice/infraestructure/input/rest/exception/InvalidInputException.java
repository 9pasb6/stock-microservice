package emazon.microservice.stock_microservice.infraestructure.input.rest.exception;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}