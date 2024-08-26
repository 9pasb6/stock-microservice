Aquí tienes el README actualizado con la inclusión de la documentación con Swagger y los tests con Mockito:

---

# Stock Microservice

## Overview

The Stock Microservice is a core component of the **Emazon** platform, responsible for managing the catalog of articles, brands, and categories. It is built using the Hexagonal Architecture (Ports and Adapters) to ensure maintainability, testability, and a clear separation of concerns.

## Architecture

### Hexagonal Architecture

The microservice follows the Hexagonal Architecture (also known as Ports and Adapters), which emphasizes the separation of business logic from technical infrastructure. This design pattern ensures that the core business logic remains isolated and can be tested independently of external systems like databases, web frameworks, or other services.

### Core Layers

- **Domain Layer**: This is the heart of the application, containing the business logic and domain models. The code in this layer is pure and independent of external frameworks. It includes:
  - **Models**: `Article`, `Brand`, `Category` represent the main entities.
  - **Ports (Interfaces)**: `IArticlePersistencePort`, `ICategoryPersistencePort`, `IBrandPersistencePort` define the operations that the domain requires from the outside world.
  - **Use Cases**: `ArticleUseCase`, `CategoryUseCase`, `BrandUseCase` implement the business rules and orchestrate the flow of data between the domain models and external components.

- **Application Layer**: This layer acts as a bridge between the domain and the external world. It includes:
  - **DTOs (Data Transfer Objects)**: Objects used to transfer data between different layers.
  - **Handlers**: `ArticleHandler`, `CategoryHandler`, `BrandHandler` handle requests from the external world (e.g., HTTP requests) and invoke the corresponding use cases.
  - **Mappers**: Convert between domain models and DTOs.

- **Infrastructure Layer**: This layer contains the technical details and infrastructure-specific implementations, such as persistence, external APIs, and web controllers. It includes:
  - **Adapters**: `ArticleJpaAdapter`, `CategoryJpaAdapter`, `BrandJpaAdapter` implement the persistence ports using JPA repositories.
  - **Entities**: `ArticleEntity`, `CategoryEntity`, `BrandEntity` represent the database entities mapped to the domain models.
  - **Configuration**: Manages external dependencies and configurations, such as database connections, OpenAPI documentation, and Bean configuration.

## Persistence Layer

The persistence layer is implemented using JPA (Java Persistence API) and MySQL. The domain layer defines the persistence ports (`IArticlePersistencePort`, `ICategoryPersistencePort`, `IBrandPersistencePort`), which are implemented in the infrastructure layer by the JPA adapters (`ArticleJpaAdapter`, `CategoryJpaAdapter`, `BrandJpaAdapter`). This separation ensures that the domain logic remains pure and unaffected by changes in the persistence mechanism.

## Database

The microservice uses a **MySQL** database managed through a Docker container. PhpMyAdmin is also included in the Docker setup for easy management and visualization of the database.

To run the database:

```bash
docker-compose up -d
```

This command will start the MySQL database and PhpMyAdmin in Docker containers as specified in the `Dockerfile`.

## Business Rules

The business rules are applied in the use cases (`ArticleUseCase`, `CategoryUseCase`, `BrandUseCase`). These rules ensure the following:

- **Article**:
  - Must be associated with at least one category and at most three categories.
  - Cannot have duplicate categories.

- **Category**:
  - Name must be unique.
  - Name length must not exceed 50 characters.
  - Description is mandatory and must not exceed 90 characters.

- **Brand**:
  - Name must be unique.
  - Name length must not exceed 50 characters.
  - Description is mandatory and must not exceed 120 characters.

## Testing

Unit tests are implemented using **Mockito** to mock dependencies and ensure the integrity of the business logic in the domain layer. These tests focus on the use cases and ensure that business rules are correctly enforced.

### Example Test

A typical unit test using Mockito might look like this:

```java
@RunWith(MockitoJUnitRunner.class)
public class ArticleUseCaseTest {

    @Mock
    private IArticlePersistencePort articlePersistencePort;

    @InjectMocks
    private ArticleUseCase articleUseCase;

    @Test
    public void testCreateArticle() {
        // Given
        Article article = new Article(1L, "Sample Article", "Description", brand, categories);
        Mockito.when(articlePersistencePort.saveArticle(article)).thenReturn(article);

        // When
        Article createdArticle = articleUseCase.createArticle(article);

        // Then
        assertEquals("Sample Article", createdArticle.getName());
        Mockito.verify(articlePersistencePort, Mockito.times(1)).saveArticle(article);
    }
}
```

This test verifies that the `ArticleUseCase` correctly interacts with the persistence port to save an article.

## Documentation

### Swagger Integration

This microservice includes Swagger for API documentation, making it easier for developers to understand and interact with the available endpoints.

- **Swagger UI**: The Swagger UI is automatically generated and can be accessed at `http://localhost:8080/swagger-ui.html`.
- **OpenAPI Configuration**: The OpenAPI documentation is configured using the `OpenAPIConfig` class, which ensures that the API documentation is generated correctly.

Swagger provides a comprehensive and interactive documentation interface that allows users to explore the API's capabilities, test endpoints, and view request/response formats.

## Project Structure

Here is a brief overview of the project structure:

```plaintext
stock-microservice/
|-- src/
|   |-- main/
|       |-- java/
|           |-- emazon/
|               |-- microservice/
|                   |-- stock/
|                       |-- dominio/
|                       |   |-- api/
|                       |   |-- modelo/
|                       |   |-- spi/
|                       |   |-- usecase/
|                       |   |-- util/
|                       |-- aplicacion/
|                       |   |-- dto/
|                       |   |-- handler/
|                       |   |-- mapper/
|                       |-- infraestructura/
|                       |   |-- controller/
|                       |   |-- jpa/
|                       |   |   |-- entity/
|                       |   |   |-- mapper/
|                       |   |   |-- repository/
|                       |   |   |-- adapter/
|                       |   |-- configuration/
|-- Dockerfile (MySQL and PhpMyAdmin)
```

## How to Run

1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   ```

2. **Build the Project**:
   ```bash
   ./mvnw clean install
   ```

3. **Run the Application**:
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Run the Database**:
   ```bash
   docker-compose up -d
   ```

5. **Access PhpMyAdmin**:
   - Visit `http://localhost:8080` to access PhpMyAdmin.

6. **Access Swagger Documentation**:
   - Visit `http://localhost:8080/swagger-ui.html` to explore the API documentation.

## Conclusion

This microservice is designed with scalability, maintainability, and testability in mind, adhering to the principles of Hexagonal Architecture. It ensures that the core business logic is clean, independent, and easily testable, while external concerns like persistence and web handling are decoupled and managed in separate layers.

Swagger provides comprehensive API documentation, and Mockito-based unit tests guarantee the integrity of business logic. These features combined make the Stock Microservice robust, reliable, and ready for integration into the broader **Emazon** platform.

---

Este README actualizado ahora incluye detalles sobre la documentación de Swagger y los tests unitarios utilizando Mockito. Si necesitas más ajustes, no dudes en pedírmelo.
