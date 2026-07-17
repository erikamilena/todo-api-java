# ToDo Management API

A modern, robust RESTful API for managing tasks and categories, built with **Java 17** and **Spring Boot 3**. 

This project was specifically designed to demonstrate advanced software engineering principles, primarily focusing on **Clean Architecture** and the **Hexagonal Architecture (Ports and Adapters)** pattern. This architectural choice ensures that the core business logic is completely isolated from external frameworks, databases, and delivery mechanisms, resulting in a highly testable, maintainable, and scalable application.

## 🚀 Technologies Used
*   **Java 17**
*   **Spring Boot 3.x** (Web, Data JPA, Security)
*   **Spring Security & JWT** for stateless authentication
*   **PostgreSQL / H2 Database**
*   **Lombok** to reduce boilerplate code
*   **Swagger / OpenAPI 3** for API documentation
*   **JUnit 5 & Mockito** for unit testing

---

## 🏗 Architecture Overview

This project is structured using **Hexagonal Architecture**, separating the application into three distinct layers. Dependencies only point **inwards** toward the Domain.

1.  **Domain Layer (`domain`)**
    *   The absolute core of the application. Contains pure Java models (`Task`, `Category`) and business rules.
    *   Defines **Ports** (interfaces like `TaskRepositoryPort`) that dictate how the domain communicates with the outside world.
    *   *Zero dependencies on Spring framework or databases.*

2.  **Application Layer (`application`)**
    *   Contains the business use cases (`TaskService`).
    *   Orchestrates the flow of data using pure domain objects. It receives commands from the outside world and uses the domain ports to fulfill them.

3.  **Infrastructure Layer (`infrastructure`)**
    *   The outermost layer containing frameworks, databases, and tools.
    *   **Inbound Adapters (REST):** The `TaskController` acts as a primary adapter, receiving HTTP requests, converting DTOs to Domain objects via `TaskRestMapper`, and routing them to the Application service.
    *   **Outbound Adapters (Persistence):** The `TaskRepositoryAdapter` implements the domain's `TaskRepositoryPort`. It manages the actual database connection using Spring Data JPA and converts Domain objects to `TaskEntity` via `TaskPersistenceMapper`.

---

## 🛠 Features
*   **Task Management:** Create, Read, Update, and Delete tasks.
*   **Advanced Filtering:** Fetch tasks by specific date ranges or filter pending tasks by category.
*   **Data Validation:** Strict DTO validation ensures data integrity before hitting the business layer.
*   **Security:** Fully secured API endpoints utilizing stateless JWT authentication.
*   **Exception Handling:** Global exception handler (`@ControllerAdvice`) provides clean, standardized JSON error responses.

---

## 💻 Getting Started

### Prerequisites
*   Java 17+
*   Maven
*   PostgreSQL (or configure `application.properties` to use the H2 in-memory database)

### Running the Application
1. Clone the repository.
2. Navigate to the project root directory.
3. Build the project using Maven:
   ```bash
   ./mvnw clean install
   ```
4. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
5. The API will start on `http://localhost:8080`.

### API Documentation (Swagger)
Once the application is running, you can explore the interactive API documentation and test endpoints directly via Swagger UI:
*   `http://localhost:8080/swagger-ui.html`

---

## 📈 Future Enhancements (Roadmap)
*   Containerize the application using **Docker**.
*   Deploy the application to **AWS** (Elastic Beanstalk / ECS).
*   Implement CI/CD pipelines using **GitHub Actions**.
