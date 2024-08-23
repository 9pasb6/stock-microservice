package emazon.microservice.stock_microservice.aplication.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandRequest {

    private String name;
    private String description;
}