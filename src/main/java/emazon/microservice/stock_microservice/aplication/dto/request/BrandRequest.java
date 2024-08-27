package emazon.microservice.stock_microservice.aplication.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BrandRequest {
    private String name;
    private String description;
}
