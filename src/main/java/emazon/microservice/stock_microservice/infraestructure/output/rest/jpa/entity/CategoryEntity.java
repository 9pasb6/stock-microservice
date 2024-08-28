package emazon.microservice.stock_microservice.infraestructure.output.rest.jpa.entity;


import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToMany
    @JoinTable(
            name = "article_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id"))
    private List<ArticleEntity> articles;
}