# Clean Architecture Refactoring Backlog

This backlog contains the tasks required to migrate the current Spring Boot project to a true Hexagonal/Clean Architecture. Completing these tasks will ensure that the domain is independent of the framework and infrastructure concerns.

## 1. Decouple Domain from Infrastructure

Currently, the `domain` layer defines a `TaskRepository` that extends Spring's `JpaRepository` and imports `TaskEntity`.

- [ ] **Create a Domain Outbound Port:** Refactor `domain/Repository/TaskRepository.java` to be a pure Java interface with no Spring annotations or Spring Data imports. Define methods like `save(Task task)`, `findById(Long id)`, and `findAll()`.
- [ ] **Move the JPA Interface:** Move the existing `JpaRepository` interface to the `infrastructure/persistence` package and rename it (e.g., `SpringDataTaskRepository`).
- [ ] **Create a Repository Adapter:** Create a new class `TaskRepositoryAdapter` inside `infrastructure/persistence` that implements the new, pure Java `TaskRepository`.
- [ ] **Implement Adapter Logic:** Inject the `SpringDataTaskRepository` into `TaskRepositoryAdapter`. The adapter should handle taking a domain `Task`, mapping it to a `TaskEntity`, and calling the Spring Data methods. It should also map the returned entities back to domain `Task` objects.

## 2. Decouple Application from Infrastructure

Currently, `TaskService` directly uses `TaskEntity` and `TaskDTO`.

- [ ] **Remove Entity References:** Update `TaskService` so it no longer imports or uses `TaskEntity`. It should rely entirely on the `TaskRepository` domain interface created in Step 1.
- [ ] **Refactor Input parameters:** Change the input parameters of `TaskService` methods. Instead of taking the `infrastructure.api.TaskDTO`, they should take the domain `Task` model, or a dedicated Application-level Command/DTO.
- [ ] **Update Controller (Inbound Adapter):** Update `TaskController` so that it maps the incoming `TaskDTO` to a domain `Task` (or application DTO) *before* passing the data to the `TaskService`. Map the returned domain model back to a `TaskDTO` right before returning the HTTP response.
- [ ] **Remove DTO References:** Remove the `infrastructure.api.TaskDTO` import from the `TaskService`.

## 3. General Cleanup & Code Quality

- [ ] **Use Case Classes:** Decide whether to use the Use Case classes (like the empty `CreateTaskUseCase` currently present). Either delete empty use case files or extract the logic from `TaskService` into individual use case classes.
- [ ] **DTO Validation:** Move manual validation checks (e.g., `task.getTitle() == null`) in `TaskService` to the DTO layer by adding annotations like `@NotBlank(message = "Title is mandatory")` on `TaskDTO`.
- [ ] **Controller Output typing:** Change `ResponseEntity<?>` to `ResponseEntity<TaskDTO>` in controller methods like `updateTask`.
- [ ] **Security Config DI:** Switch the `@Autowired` injection of `JwtAuthFilter` in `SecurityConfig` to constructor injection using Lombok's `@RequiredArgsConstructor`.
- [ ] **Method References:** Replace lambda expressions in Spring Security config (like `.csrf(csrf -> csrf.disable())`) with method references (`.csrf(AbstractHttpConfigurer::disable)`).
