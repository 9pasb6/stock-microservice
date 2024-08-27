package emazon.microservice.stock_microservice.aplication.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRequest {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private Long brandId;
    private List<Long> categoryIds;


}