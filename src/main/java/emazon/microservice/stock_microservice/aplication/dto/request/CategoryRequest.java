package emazon.microservice.stock_microservice.aplication.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    private String name;
    private String description;


}