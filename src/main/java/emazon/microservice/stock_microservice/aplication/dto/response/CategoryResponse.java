package emazon.microservice.stock_microservice.aplication.dto.response;

import lombok.*;

@Getter
@Setter
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
}