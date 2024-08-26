package emazon.microservice.stock_microservice.aplication.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

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
    private List<Long> categoryIds;
}