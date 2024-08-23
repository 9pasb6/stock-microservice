package emazon.microservice.stock_microservice.aplication.dto.request;


import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ArticleRequest {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private Long brandId;
    private Set<Long> categoryIds;
}