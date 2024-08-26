package emazon.microservice.stock_microservice.aplication.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class BrandRequest {
    private String name;
    private String description;
}
