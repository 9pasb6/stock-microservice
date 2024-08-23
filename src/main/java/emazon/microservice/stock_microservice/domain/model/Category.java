package emazon.microservice.stock_microservice.domain.model;

import java.util.Set;

public class Category {

    private Long id;
    private String name;
    private String description;
    private Set<Long> articleIds; // Agregado para reflejar relación muchos a muchos con Article.

    // Constructors
    public Category() {
    }



    public Category(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Category(Long id, String name, String description, Set<Long> articleIds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.articleIds = articleIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Long> getArticleIds() {
        return articleIds;
    }

    public void setArticleIds(Set<Long> articleIds) {
        this.articleIds = articleIds;
    }
}