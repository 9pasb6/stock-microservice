package emazon.microservice.stock_microservice.infraestructure.input.rest.controller;

import emazon.microservice.stock_microservice.aplication.dto.request.ArticleRequest;
import emazon.microservice.stock_microservice.aplication.dto.response.ArticleResponse;
import emazon.microservice.stock_microservice.aplication.handler.IArticleHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final IArticleHandler articleHandler;

    @PostMapping
    @Operation(summary = "Create a new article", description = "Allows an admin to create a new article with specified details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Article created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<Void> saveArticle(@RequestBody ArticleRequest articleRequest) {
        articleHandler.saveArticle(articleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Operation(summary = "Get all articles", description = "Retrieve a list of all articles, ordered as specified.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of articles retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ArticleResponse>> getAllArticles(
            @RequestParam(defaultValue = "asc") String order) {
        List<ArticleResponse> articles = articleHandler.getAllArticles(order);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an article by ID", description = "Retrieve details of a specific article by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Article not found")
    })
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable Long id) {
        ArticleResponse article = articleHandler.getArticle(id);
        return ResponseEntity.ok(article);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an article", description = "Update the details of an existing article.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<Void> updateArticle(@RequestBody ArticleRequest articleRequest) {
        articleHandler.updateArticle(articleRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an article", description = "Delete an article by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Article not found")
    })
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleHandler.deleteArticle(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/brand")
    @Operation(summary = "Get articles by brand", description = "Retrieve a list of articles by brand name, ordered as specified.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of articles retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid brand name")
    })
    public ResponseEntity<List<ArticleResponse>> getAllByBrandName(
            @RequestParam String brandName,
            @RequestParam(defaultValue = "asc") String order) {
        List<ArticleResponse> articles = articleHandler.getAllByBrandName(brandName, order);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/category")
    @Operation(summary = "Get articles by category", description = "Retrieve a list of articles by category name, ordered as specified.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of articles retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid category name")
    })
    public ResponseEntity<List<ArticleResponse>> getAllByCategoryName(
            @RequestParam String categoryName,
            @RequestParam(defaultValue = "asc") String order) {
        List<ArticleResponse> articles = articleHandler.getAllByCategoryName(categoryName, order);
        return ResponseEntity.ok(articles);
    }
}