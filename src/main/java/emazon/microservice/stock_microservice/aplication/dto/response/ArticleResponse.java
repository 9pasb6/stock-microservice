package emazon.microservice.stock_microservice.aplication.dto.response;


import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ArticleResponse {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private BrandResponse brand; // DTO para la marca
    private List<CategoryResponse> categories;

}