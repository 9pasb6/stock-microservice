package emazon.microservice.stock_microservice.aplication.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryRequest {

    private String name;
    private String description;


}